package com.projet.utils;

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
public class DateManager {

    public static int compareToCurrentDate(Date date) {
        Date currentDate = new Date();

        return date.compareTo(currentDate);
    }

    public static int getYear(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH);
    }

    public static Date getMonthAndYearDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.set(Calendar.MONTH, getMonth(date));
        calendar.set(Calendar.DATE, 0);

        return calendar.getTime();
    }
}
