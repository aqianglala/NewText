package com.example.qiang_pc.newtalkpal.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by admin on 2016/2/2.
 */
public class DateUtils {

    /**
     * 2015-11-25T08:40:42.166Z------->Date
     * @param date
     * @return
     */
    public static Date getDate(String date) {
        final String year = date.substring(0, 4);
        final String month = date.substring(5, 7);
        final String day = date.substring(8, 10);
        final String hour = date.substring(11, 13);
        final String minute = date.substring(14, 16);
        final String second = date.substring(17, 19);
        final int millisecond = Integer.valueOf(date.substring(20, 23));
        Calendar result =
                new GregorianCalendar(Integer.valueOf(year),
                        Integer.valueOf(month) - 1, Integer.valueOf(day),
                        Integer.valueOf(hour), Integer.valueOf(minute),
                        Integer.valueOf(second));
        result.set(Calendar.MILLISECOND, millisecond);
        result.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Date datess = new Date(result.getTimeInMillis());
        return datess;
    }

    /**
     * 2015-11-25T08:40:42.166Z------->yyyy'年'MM'月'dd'日'HH:mm
     * 有时区计算，转换为UTC时间格式
     * @param date
     *            the String to read the date from
     * @return a calendar representing the date found in the string
     */
    public static String getStringToCal(String date) {
        final String year = date.substring(0, 4);
        final String month = date.substring(5, 7);
        final String day = date.substring(8, 10);
        final String hour = date.substring(11, 13);
        final String minute = date.substring(14, 16);
        final String second = date.substring(17, 19);
        final int millisecond = Integer.valueOf(date.substring(20, 23));
        Calendar result =
                new GregorianCalendar(Integer.valueOf(year),
                        Integer.valueOf(month) - 1, Integer.valueOf(day),
                        Integer.valueOf(hour), Integer.valueOf(minute),
                        Integer.valueOf(second));
        result.set(Calendar.MILLISECOND, millisecond);
        result.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Date datess = new Date(result.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH:mm");
        return format.format(datess);
    }

}
