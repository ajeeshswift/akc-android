package com.swift.akc.utils;

public final class DateUtils {

    private DateUtils() {

    }
    public static String convertDateFormat(String date){
        String sd[] = date.split("/");
        String day = sd[0];
        if(day.length() == 1)
            day = "0"+day;
        String month = sd[1];
        if(month.length() == 1)
            month = "0"+month;
        String year = sd[2];
        return year+"-"+month+"-"+day;
    }
}
