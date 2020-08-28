package com.projet.services;

import com.projet.entities.Card;
import com.projet.entities.Dashboard;
import com.projet.entities.Role;
import com.projet.entities.User;
import com.projet.enumeration.RoleEnum;
import org.apache.log4j.Logger;

import javax.persistence.StoredProcedureQuery;
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

    public CardService cardService =  new CardService(Card.class);

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

    public User getByEmail(String email) {
        Map<String, String> param = new HashMap<>();
        param.put("email", email);

        return finder.findOneByNamedQuery("User.findUserByEmail", param);
    }

    public void addUserFinancialAccount(User user) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("addUserFinancialAccount");
        query.setParameter("user", user.getId());
        query.execute();
    }

    public void addUserSupplier(User user) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("addUserSupplier");
        query.setParameter("user", user.getId());
        query.execute();
    }

    public void addUserDiary(User user) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("addUserDiary");
        query.setParameter("user", user.getId());
        query.execute();
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

    public User createUser(User user) {
        Dashboard dashboard = new Dashboard();
        List<Card> cards = this.cardService.findAllCards();
        dashboard.setLabel("Mon dashboard");
        dashboard.setCards(cards);
        user.addDashboard(dashboard);

        RoleService service = new RoleService(Role.class);

        Role role = service.findByLabel(RoleEnum.USER);

        user.setRole(role);

        return user;
    }
}