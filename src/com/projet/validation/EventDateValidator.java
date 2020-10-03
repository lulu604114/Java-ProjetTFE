package com.projet.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDateTime;

/**
 * @created 15/09/2020 - 22:10
 * Projet TFE
 * Amaury
 **/
@FacesValidator("eventDateValidator")
public class EventDateValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        LocalDateTime startDate = (LocalDateTime) uiComponent.getAttributes().get("startDate");
        LocalDateTime endDate = (LocalDateTime) uiComponent.getAttributes().get("endDate");

        if (endDate.isAfter(startDate)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO, null, null));
        }
    }
}
