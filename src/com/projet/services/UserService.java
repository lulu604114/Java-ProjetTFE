package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.PasswordMatcher;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author amaury
 * @project JSF-TFE
 * Date: 20/11/2019
 * Time: 18:00
 * =================================================================
 */
public class UserService implements  Serializable {
    private static final Logger log = Logger.getLogger(UserService.class);
    private static final long serialVersionUID = 1L;

    private List<User> users;
    private User userDB;
    private EntityManager em;

    public List<User> getAll() {
        EntityFinder<User> finder = new EntityFinderImpl<>(User.class);
        users = finder.findByNamedQuery("User.findAll", null);
        return users;
    }

    public User getByUsername(String username) {
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        EntityFinder<User> finder = new EntityFinderImpl<>(User.class);

        return finder.findOneByNamedQuery("User.findUserByUsername", param);
    }

    public User getById(Integer id) {
        return null;
    }

    public boolean create(User user) {
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean status = false;
        try {
            PasswordMatcher auth = new PasswordMatcher();
            this.userDB.setPassword(auth.getPasswordService().encryptPassword(user.getPassword()));
            em.persist(user);
            em.getTransaction().commit();
            status = true;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                status = false;
            }
            em.close();
            log.debug("Ok");
        }
        return status;
    }

    public boolean update(User user) throws Exception {
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean status = false;
        try {
            this.userDB = em.find(User.class, user.getId());
            if (this.userDB != null) {
                PasswordMatcher auth = new PasswordMatcher();
                this.userDB.setPassword(auth.getPasswordService().encryptPassword(user.getPassword()));
                this.userDB.setEmail(user.getEmail());
                this.userDB.setFirstName(user.getFirstName());
                this.userDB.setLastName(user.getLastName());
                this.userDB.setUsername(user.getUsername());
                em.merge(userDB);
                em.getTransaction().commit();
                status = true;
            }
        }finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                status = false;
            }
            em.close();
            log.debug("Ok");
        }
        return status;
    }

    public boolean deleteByID(User user) {
        em = EMF.getEM();
        em.getTransaction().begin();
        boolean status = false;
        try{
            User userDB = em.find(User.class, user.getId());
            if (userDB != null) {
                em.remove(userDB);
                status = true;
            } else {
                status = false;
            }
            em.getTransaction().commit();

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.error("Rollback");
                status = false;
            }
            em.close();
            log.debug("Ok");
        }
        return status;
    }
}