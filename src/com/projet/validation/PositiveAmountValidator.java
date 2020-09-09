package com.projet.validation;

import com.projet.conf.App;
import com.projet.utils.Message;

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
 * Date: 09/09/2020
 * Time: 17:44
 * =================================================================
 */
@FacesValidator("positiveAmountValidator")
public class PositiveAmountValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        if (o == null) {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur", "Veuillez renseigner ce champ"));
        } else {

            if (Double.parseDouble(o.toString()) <= 0 ) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur", "Ce champ doit être au moins supérieur à zéro"));
            }

        }

    }
}
