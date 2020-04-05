package com.projet.validation;

import com.projet.conf.App;
import com.projet.controllers.utils.Message;
import com.projet.entities.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.subject.Subject;

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
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        Subject subject = SecurityUtils.getSubject();
        User sessionUser = (User) subject.getSession().getAttribute(App.SESSION_USER);

        UIInput passwordInput = (UIInput) uiComponent.findComponent("password");
        String password = (String) passwordInput.getLocalValue();

        UIInput newPasswordInput = (UIInput) uiComponent.findComponent("newPassword");
        String newPassword = (String) newPasswordInput.getLocalValue();

        String newPasswordConfirm = (String) value;

        if (password == null || newPassword == null || !newPassword.equals(newPasswordConfirm)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("Erreur"), message.translate("Mot de passe ne sont pas identique")));
        } else {
            PasswordMatcher matcher = new PasswordMatcher();

            if (!matcher.getPasswordService().passwordsMatch(password, sessionUser.getPassword())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("Erreur"), message.translate("Mot de passe incorrect")));
            }
        }
    }
}
