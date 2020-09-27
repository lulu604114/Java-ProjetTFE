package com.projet.validation;

import com.projet.conf.App;
import com.projet.utils.Message;
import org.iban4j.*;

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
 * Date: 26/09/2020
 * Time: 21:48
 * =================================================================
 */
@FacesValidator("ibanValidator")
public class IbanValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o != null) {
            String iban = o.toString();

            try {
                IbanUtil.validate(iban, IbanFormat.Default);
            } catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException e) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("Iban non valide"), null));
            }
        }
    }
}
