package com.bca.notiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Caching {

    private static SharedPreferences pref;
    public final static String CACHE = "notiz_cache";
    public final static String FIRST_LAUNCH = "FIRST_LAUNCH";
    public final static String LOGGED_IN = "LOGGED_IN";

    private static SharedPreferences getInstance(Context context) {
        if (pref == null) pref = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
        return pref;
    }

    public static boolean isFirstLaunch(Context context) {

        return getInstance(context).getBoolean(FIRST_LAUNCH, true);
    }

    public static void setFirstLaunch(Context context, boolean value) {

        getInstance(context).edit().putBoolean(FIRST_LAUNCH, false).apply();
    }

    public static void setLoggedIn(Context context, boolean value) {
        getInstance(context).edit().putBoolean(LOGGED_IN, value).apply();
    }

    public static boolean isLoggedIn(Context context) {
        return getInstance(context).getBoolean(LOGGED_IN, false);
    }

}
