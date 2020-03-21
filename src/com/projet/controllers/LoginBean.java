package com.projet.controllers;

import com.projet.conf.Config;
import com.projet.controllers.utils.Message;
import com.projet.entities.User;
import com.projet.services.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 06/03/2020
 * Time: 13:38
 * =================================================================
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LoginBean.class);
    private static final Message message = Message.getMessage(Config.BUNDLE_MESSAGE);

    private String username;
    private String password;
    private boolean rememberMe;

    /**
     *
     * @return
     * @throws IOException
     */
    public void doLogin() throws IOException {
        String view;

        Subject currentUser = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);

        try {
            currentUser.login(token);

            UserService service = new UserService();

            saveUserInSession(currentUser.getSession(), service.getByUsername(username));

            view = defineViewByRole(currentUser, "admin");

            FacesContext.getCurrentInstance().getExternalContext().redirect(view);

        } catch (UnknownAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (IncorrectCredentialsException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (LockedAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Error", "Contact admin");
        } catch (AuthenticationException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Unknown error", "Contact administrator");
        } finally {
            token.clear();
        }

    }

    public void doLogout() throws IOException {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            subject.logout();
            context.invalidateSession();
            context.redirect("/index.xhtml");
        }
    }

    public String isLogged() {
        Subject currentUser = SecurityUtils.getSubject();
        String view;

        if (currentUser.isAuthenticated()) {
            view = defineViewByRole(currentUser, "admin");
        } else
            view = "/login.xhtml";

        return view + "?faces-redirect=true";
    }

    private String defineViewByRole(Subject currentUser, String role) {
        if (currentUser.hasRole(role))
            return "/app/admin/dashboard.xhtml";
        else
            return "/app/dashboard.xhtml";
    }

    private void saveUserInSession(Session session, User user) {
        session.setAttribute(Config.SESSION_USER, user);
        log.debug(user + " save in session : " + session.getId() + " with time-out: " + session.getTimeout());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}