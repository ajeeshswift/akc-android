package com.swift.akc.extras;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.swift.akc.network.data.AdminVO;
import com.swift.akc.network.data.HarvestFarmVO;

public class Storage {

    private static final String KEY_ADMIN = "key_admin";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_APP_CONTEXT = "key_app_context";
    public static String TOKEN;

    public static String selectedMenu = null;

    public static HarvestFarmVO selectedHarvestFarm = null;

    public static void save(Context context, AdminVO adminVO) {
        Gson gson = new Gson();
        String adminVOStr = gson.toJson(adminVO);
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_APP_CONTEXT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ADMIN, adminVOStr);
        editor.putString(KEY_TOKEN,adminVO.getToken());
        TOKEN = adminVO.getToken();
        editor.apply();
        editor.commit();
    }

    public void reset() {

    }

    public static void clear(Context context) {
        TOKEN = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_APP_CONTEXT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
