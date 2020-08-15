package com.projet.validation;

import com.projet.conf.App;
import com.projet.controllers.utils.Message;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.subject.Subject;
import org.primefaces.util.SecurityUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE-Template
 * Date: 03/04/2020
 * Time: 17:26
 * =================================================================
 */
@FacesValidator("passwordChangeValidator")
public class PasswordChangeValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_VALIDATOR_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        User sessionUser = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);

        String password = (String) o;

        PasswordMatcher matcher = new PasswordMatcher();

        if (!matcher.getPasswordService().passwordsMatch(password, sessionUser.getPassword())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("Erreur impossible de modifier votre mot de passe : le mot de passe que vous avez introduit est incorrect"), null));
        }
    }
}
