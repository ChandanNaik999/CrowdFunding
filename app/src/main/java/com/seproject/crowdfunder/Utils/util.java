package com.seproject.crowdfunder.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.seproject.crowdfunder.models.User;


public class util {
    static public String SHARED_PREFERNCES_USER_DETAILS = "User Details";
    static public String SHARED_PREFERNCES_USER_DETAILS_EMAIL = "email";
    static public String SHARED_PREFERNCES_USER_DETAILS_NAME = "name";
    static public String SHARED_PREFERNCES_USER_DETAILS_PROFILE_PIC_PATH = "profile";
    static public String SHARED_PREFERNCES_USER_DETAILS_RATING = "rating";
    static public String SHARED_PREFERNCES_USER_DETAILS_UID = "000";


    public static String email;
    static public User user = new User();




    static public void writeIntoSharedPref(Context context, String sharedPrefFilename, String key, String value, int mode){
        SharedPreferences pref = context.getSharedPreferences(sharedPrefFilename, mode); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static public void writeIntoSharedPref(Context context, String sharedPrefFilename, String key, float value, int mode){
        SharedPreferences pref = context.getSharedPreferences(sharedPrefFilename, mode); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
}
