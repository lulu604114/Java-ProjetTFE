package com.projet.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.*;

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

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM YYYY");
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM hh:mm");
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    public static boolean compareToCurrentDate(Date date) {
        Date currentDate = new Date();

        return date.before(currentDate);
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


    public static String formatMonth(int month, Locale locale) {
        return Month.of(month).getDisplayName(TextStyle.FULL, locale);
    }

    /**
     * To date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * To local date time local date time.
     *
     * @param calendar the calendar
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    /**
     * To calendar calendar.
     *
     * @param localDateTime the local date time
     * @return the calendar
     */
    public static Calendar toCalendar(LocalDateTime localDateTime) {
        Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static Date addYear(Date date, int nbYear) {
        return addToDate(date, Calendar.YEAR, nbYear);
    }

    public static Date addMonth(Date date, int nbMonth) {
        return addToDate(date, Calendar.MONTH, nbMonth);
    }

    public static Date addDay(Date date, int nbDay) {
        return addToDate(date, Calendar.DAY_OF_MONTH, nbDay);
    }

    private static Date addToDate(Date date, int type, int numberToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(type, numberToAdd);
        return cal.getTime();
    }

    public static Date getFirstDateOfMonth(Date date) {
        return getFirstDateOf(date, Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfMonth(Date date) {
        return getLastDateOf(date, Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfYear(Date date) {
        return getLastDateOf(date, Calendar.DAY_OF_YEAR);
    }

    public static Date getFirstDateOfYear(Date date) {
        return getFirstDateOf(date, Calendar.DAY_OF_YEAR);
    }

    private static Date getLastDateOf(Date date, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(type, cal.getActualMaximum(type));
        return cal.getTime();
    }

    private static Date getFirstDateOf(Date date, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(type, cal.getActualMinimum(type));
        return cal.getTime();
    }
}