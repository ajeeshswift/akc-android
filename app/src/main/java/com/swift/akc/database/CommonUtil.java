package com.swift.akc.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    public static final String MyPreferances = "PREFERENCE";
    public static SharedPreferences pref;
    public static Context context;
    public static DatabaseHelper databaseHelper;
    public static DatabaseUtil databaseUtil;

    public CommonUtil(Context context) {
        CommonUtil.context = context;
        pref = CommonUtil.context.getSharedPreferences(MyPreferances, Context.MODE_PRIVATE);
    }
    public static boolean isOnline(Context con){
        ConnectivityManager cm =((ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }



}
