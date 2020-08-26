package com.projet.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 25/08/2020
 * Time: 17:40
 * =================================================================
 */
public class Utility {

    //Check
    public static int compareToCurrentDate(Date date) {
        Date currentDate = new Date();

        return date.compareTo(currentDate);
    }

    public static int getYear(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }
}
