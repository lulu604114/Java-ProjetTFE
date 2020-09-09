package com.projet.validation;

import com.projet.conf.App;
import com.projet.utils.Message;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 09/09/2020
 * Time: 09:06
 * =================================================================
 */
@FacesValidator("dueAtDateValidator")
public class DueAtDateValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o != null) {

            UIInput createdAtInput = (UIInput) uiComponent.findComponent("createdAt");

            Date dueAt = (Date) o;
            Date createdAt = null;

            if (createdAtInput.getSubmittedValue() != null) {
                try {
                    createdAt = new SimpleDateFormat("dd/MM/yyyy").parse((String) createdAtInput.getSubmittedValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                createdAt = (Date) createdAtInput.getLocalValue();
            }

            if (dueAt.before(createdAt)) {
                FacesMessage message = new FacesMessage("Erreur", "Ce champ ne peut être antérieur à la date d'achat");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                throw new ValidatorException(message);
            }
        }

    }
}
