package com.projet.conf;

import com.projet.controllers.LoginBean;
import com.projet.entities.User;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 12/03/2020
 * Time: 22:57
 * =================================================================
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class);


    public static final String SESSION_USER = "sessionUser";
    public static final String BUNDLE_MESSAGE = "resources.messages";
    public static final String BUNDLE_VALIDATOR_MESSAGE = "resources.messages";
    public static final String BUNDLE_VIEW = "resources.view";

    /**
     * Retrieve user save in session
     *
     * @return session's user
     */
    public static User getSessionUser() {
        Subject sessionUser = SecurityUtils.getSubject();
        return (User) sessionUser.getSession().getAttribute(SESSION_USER);
    }

    /**
     * saves user in session
     *
     * @param session to save in
     * @param user  to save in the session
     */
    public static void saveUserInSession(Session session, User user) {
        session.setAttribute(SESSION_USER, user);
        log.debug(user + " save in session : " + session.getId() + " with time-out: " + session.getTimeout());
    }

}
