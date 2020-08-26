package com.projet.validation;

import com.projet.conf.App;
import com.projet.utility.Message;
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
 * Date: 12/08/2020
 * Time: 18:22
 * =================================================================
 */
@FacesValidator("EmailNotUsedValidator")
public class EmailNotUsedValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String email = (String) o;

        UserService service = new UserService(User.class);

        try {
            if (service.getByEmail(email) != null)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate(email + " est déjà utilisé"), null));

        } finally {
            service.close();
        }
    }
}
