package com.projet.security;

import com.projet.conf.App;
import com.projet.controllers.utils.Message;
import com.projet.services.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.faces.application.FacesMessage;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 05/04/2020
 * Time: 12:16
 * =================================================================
 */
public class SecurityManager {
    private static final Logger log = Logger.getLogger(SecurityManager.class);
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    public static boolean processToLogin(String username, String password, boolean rememberMe) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);

        try {
            subject.login(token);

            return true;

        } catch (UnknownAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (IncorrectCredentialsException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (LockedAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Error", "Contact admin");
        } catch (ExcessiveAttemptsException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Unknown error", "Contact administrator");
        } finally {
            token.clear();
        }

        return false;
    }

    public static boolean processToLogout() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            subject.getSession().stop();
            return true;
        }

        return false;
    }

    public static boolean userIsLogged() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static String userIsRemembered() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isRemembered())
            return (String) subject.getPrincipal();

        return null;
    }

    public static Object getSessionAttribute(String name) {
        Session session = SecurityUtils.getSubject().getSession();

        Object attribute = session.getAttribute(name);

        if (attribute == null)
            log.debug("No such attribute named : " + name + "find in session : " + session.getId());
        else
            log.debug(name + " retrieve from session : " + session.getId());

        return attribute;
    }

    public static void saveAttributeInSession(String name, Object value) {
        Session session = SecurityUtils.getSubject().getSession();

        session.setAttribute(name, value);

        log.debug("Attribute " + name + " with value = " + value + " save in session : " + session.getId() + " with time-out: " + session.getTimeout());
    }

    public static String encryptPassword(String password) {
        PasswordMatcher matcher = new PasswordMatcher();

        return matcher.getPasswordService().encryptPassword(password);
    }
}