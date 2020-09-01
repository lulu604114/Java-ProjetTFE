package com.projet.validation;

import com.projet.conf.App;
import com.projet.utils.Message;
import com.projet.entities.User;
import com.projet.services.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 09/08/2020
 * Time: 21:36
 * =================================================================
 */
@FacesValidator("usernameNotUsedValidator")
public class UsernameNotUsedValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String username = (String) o;

        UserService service = new UserService(User.class);

        try {
            if (service.getByUsername(username) != null)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate(username + " est déjà utilisé"), null));

        } finally {
            service.close();
        }
    }
}
