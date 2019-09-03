package com.beazle.pursuitvolley.DateFormatter;

import java.text.DateFormat;
import java.util.Date;

public final class DateFormatter {

    private DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT);
    private DateFormat dateFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private DateFormat dateFormatLong = DateFormat.getDateInstance(DateFormat.LONG);
    private DateFormat dateFormatFull = DateFormat.getDateTimeInstance();

    public String GetDateShortFormat(Date date) { return dateFormatShort.format(date); }

    public String GetDateMediumFormat(Date date) {
        return dateFormatMedium.format(date);
    }

    public String GetDateLongFormat(Date date) { return dateFormatLong.format(date); }

    public String GetDateFullFormat(Date date) { return dateFormatFull.format(date); }
}
