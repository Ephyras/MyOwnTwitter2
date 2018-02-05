package com.example.javier.mot2;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Javier on 2/5/2018.
 */
//Taken from http://androidkt.com/datetime-datatype-sqlite-using-room/
public class TimeStampConverter {
    static DateFormat df = new SimpleDateFormat(Utils.DATE_FORMAT, Locale.US);

    @TypeConverter
    public static Date toDate(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        if (date != null) {
            return df.format(date);
        } else {
            return null;
        }
    }
}