package com.beazle.pursuitvolley.DateFormatter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateFormatter {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
    public static SimpleDateFormat playerScheduleSelectionDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    private static DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT);
    private static DateFormat dateFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private static DateFormat dateFormatLong = DateFormat.getDateInstance(DateFormat.LONG);
    private static DateFormat dateFormatFull = DateFormat.getDateTimeInstance();

    public static String ConvertDateObjectToStringFormat(Date date) {
        return formatter.format(date);
    }

    public static Date ConvertStringFormatToDateObject(String date) {
        return formatter.parse(date, new ParsePosition(0));
    }

    public static String GetDateShortFormat(Date date) { return dateFormatShort.format(date); }

    public static String GetDateMediumFormat(Date date) {
        return dateFormatMedium.format(date);
    }

    public static String GetDateLongFormat(Date date) { return dateFormatLong.format(date); }

    public static String GetDateFullFormat(Date date) { return dateFormatFull.format(date); }
}

