package com.projet.controllers;

import com.projet.conf.App;
import com.projet.utils.Message;
import com.projet.security.SecurityManager;
import com.projet.services.UserService;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
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
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private String username;
    private String password;
    private boolean rememberMe;


    /**
     *
     * @return
     * @throws IOException
     */
    public void doLogin() throws IOException {

        if (SecurityManager.processToLogin(username, password, rememberMe)) {
            UserService service = new UserService();
            SecurityManager.saveAttributeInSession(App.SESSION_USER, service.getByUsername(username));
            FacesContext.getCurrentInstance().getExternalContext().redirect("/app/dashboard/dashboard.xhtml");
        }
    }

    public void doLogout() throws IOException {
        if (SecurityManager.processToLogout()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
        }
    }

    public String isLogged() {
        String view;

        if (SecurityManager.userIsLogged()) {
            view = "/app/dashboard.xhtml";
        } else {
            view = "/login.xhtml";

            String username = SecurityManager.userIsRemembered();

            if (username != null)
                setUsername(username);
        }

        return view + "?faces-redirect=true";
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