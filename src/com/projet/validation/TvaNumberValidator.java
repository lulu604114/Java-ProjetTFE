package com.projet.validation;

import ch.digitalfondue.vatchecker.EUVatCheckResponse;
import ch.digitalfondue.vatchecker.EUVatChecker;
import com.projet.conf.App;
import com.projet.utils.Message;
import com.sun.xml.internal.bind.v2.TODO;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.xml.soap.SOAPPart;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 03/09/2020
 * Time: 10:45
 * =================================================================
 */
@FacesValidator("tvaNumberValidator")
public class TvaNumberValidator implements Validator {
    private Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    private final int COUNTRY_CODE_INDEX = 0;
    private final int COUNTRY_CODE_LENGTH = 2;
    private final int VAT_NUMBER_INDEX = 2;
    private final int CHECK_DIGIT_INDEX = 8;
    private final int VAT_NUMBER_INT_INDEX = 0;
    private final int VAT_NUMBER_INT_LENGTH = 8;
    private final int MODULO = 97;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o != null) {
            int error = 0;


            String value = o.toString();                                                                       // BE0684715773
            String countryCode = value.substring(COUNTRY_CODE_INDEX, COUNTRY_CODE_LENGTH);                     // BE
            String vatNumber = value.substring(VAT_NUMBER_INDEX);                                              // 0684715773

            EUVatCheckResponse response = EUVatChecker.doCheck(countryCode, vatNumber);

            // if response is not valid
            if (! response.isValid()) {

                // if there is an error in response
                if (response.isError()) {
                    if (!response.getFault().equals(EUVatCheckResponse.FaultType.INVALID_INPUT)) {
                        int checkDigit = Integer.parseInt(vatNumber.substring(CHECK_DIGIT_INDEX));
                        int vatNumberInt = Integer.parseInt(vatNumber.substring(VAT_NUMBER_INT_INDEX, VAT_NUMBER_INT_LENGTH));

                        int result = vatNumberInt % MODULO;

                        // if result not equal to check digit it set an error
                        if (MODULO - result != checkDigit)
                            error++;
                    }
                    // if there is no error and vat is invalid it set an error
                } else {
                    error++;
                }
            }

            if (error != 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate("Numero de tva non valide"), null));
        }
    }
}
