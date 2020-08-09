package com.projet.services;

import com.projet.connection.EMF;
import com.projet.dao.EntityFinder;
import com.projet.dao.EntityFinderImpl;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.PasswordMatcher;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class UserService extends Service<User> implements Serializable {
    private static final Logger log = Logger.getLogger(UserService.class);
    private static final long serialVersionUID = 1L;

    public UserService(Class<?> ec) {
        super(ec);
    }

    public List<User> getAll() {
        return finder.findByNamedQuery("User.findAll", null);
    }

    public User getByUsername(String username) {
        Map<String, String> param = new HashMap<>();
        param.put("username", username);

        return finder.findOneByNamedQuery("User.findUserByUsername", param);
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            em.persist(user);
        } else {
            user = em.merge(user);
        }
        return user;
    }
}