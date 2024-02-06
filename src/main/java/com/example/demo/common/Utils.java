package com.example.demo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static Date str2date(String str) {
        long ms = 0;
        try {
            ms = SDF.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(ms);
    }
    
}
