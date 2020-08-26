package com.projet.utility;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE
 * Date: 20/02/2020
 * Time: 15:54
 * =================================================================
 */public class Message {

    private String bundle;

    /* Private constructor */
    private Message() {
    }

    /**
     * HOLDER
     * internal class will only be loaded into memory when it's referred to for the first time,
     * that is to say during the first call to "getInstance ()".
     * When loading, the Holder will initialize its static fields and therefore create the single instance
     */
    private static class MessageHolder {
        private final static Message instance = new Message();
    }

    /* Access to the instance */
    public static Message getMessage(String bundle) {
        MessageHolder.instance.setBundle(bundle);
        return MessageHolder.instance;
    }

    public void display(FacesMessage.Severity severity, String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, translate(summary), null));
    }

    public void display(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, translate(summary), translate(detail)));
    }

    public String translate(String text) {
        Enumeration<String> keys = getBundle().getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.equals(text))
                return getBundle().getString(text);
        }

        return text;
    }

    private ResourceBundle getBundle() {
        return ResourceBundle.getBundle(bundle, FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    private void setBundle(String bundle) {
        this.bundle = bundle;
    }
}
