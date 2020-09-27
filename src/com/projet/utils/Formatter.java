package com.projet.utils;

import org.iban4j.Iban;
import org.iban4j.IbanFormat;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 27/09/2020
 * Time: 16:38
 * =================================================================
 */
public class Formatter {
    public static String formatIbanToDisplay(String ibanString) {
        if (ibanString != null) {
            Iban iban = Iban.valueOf(ibanString);

            return iban.toFormattedString();
        }

        return null;
    }

    public static String formatIbanForPersist(String ibanString) {
        if (!ibanString.equals("")) {
            Iban iban = Iban.valueOf(ibanString, IbanFormat.Default);

            return iban.toString();
        }

        return null;
    }
}
