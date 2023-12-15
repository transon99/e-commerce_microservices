package com.sondev.authservice.utils;

import java.util.Date;

public class TempEmailUtils {
    public static String createTempEmail (){
        return "temp_email" + new Date().getTime() + "@gmail.com";
    }
}
