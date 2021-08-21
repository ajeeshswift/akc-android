package com.swift.akc.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "akc";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    public static final String HARVEST_TABLE = "com_farm_flora_start";

    // below variable is for our id column.

    public static final String FLORA_ST_ID          = "flora_st_id";
    public static final String FARM_ID              = "Farm_id";
    public static final String PLANTSEED            = "plant_id";
    public static final String SOWING_DATE          = "issue_dt";
    public static final String ISSUE_BY             = "issue_by";
    public static final String SAPPLING_QUANTITY    = "issue_size";
    public static final String EUID                 = "euid";
    public static final String EDTM                 = "edtm";
    public static final String UID                  = "uid";
    public static final String DTM                  = "dtm";
    public static final String ENTRY_DATE           = "entry_date";
    public static final String VLG_ID               = "Vlg_id";
    public static final String PLANT_GROUP_ID       = "plant_group_id";
    public static final String PROJECT_ID           = "project_id";
    public static final String VERSION              = "version";
    public static final String HARVEST_DATE         = "harvest_date";
    public static final String HARVEST_QUANTITY     = "harvest_quantity";
    public static final String OWN_HOME_USE         = "own_use";
    public static final String SOLD_QUANTITY        = "sold_quantity";
    public static final String SOLD_RATE            = "sold_rate";
    public static final String TOTAL_INCOME         = "sold_income";
    public static final String FLORA_ST_ID_M        = "flora_st_id_m";
    public static final String STATUS               = "status";

    public static final String  HARVEST_QUERY ="CREATE TABLE "
            + HARVEST_TABLE + "(" + FLORA_ST_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ," +FARM_ID
            + " TEXT, "+ PLANTSEED
            + " TEXT, "+ SOWING_DATE
            + " TEXT, "+ ISSUE_BY
            + " TEXT, "+ SAPPLING_QUANTITY
            + " TEXT, "+ EUID
            + " TEXT, "+ EDTM
            + " TEXT, "+ UID
            + " TEXT, "+ DTM
            + " TEXT," + ENTRY_DATE
            + " TEXT," + VLG_ID
            + " TEXT," + PLANT_GROUP_ID
            + " TEXT," + PROJECT_ID
            + " TEXT," + VERSION
            + " TEXT," + HARVEST_DATE
            + " TEXT," + HARVEST_QUANTITY
            + " TEXT," + OWN_HOME_USE
            + " TEXT," + SOLD_QUANTITY
            + " TEXT," + SOLD_RATE
            + " TEXT," + TOTAL_INCOME
            + " TEXT," + FLORA_ST_ID_M
            + " TEXT," + STATUS
            + " TEXT);";




    // below variable is for our table name.
    public static final String HARVEST_FORCASTING_TABLE = "com_hvst_forecasting";

    // below variable is for our id column.

    public static final String ID_CL                          = "id";
    public static final String FORECAST_FARM_ID               = "farm_id";
    public static final String FORECAST_PLANT_SEED            = "plant_id";
    public static final String FORECAST_SOWING_KG             = "seeds";
    public static final String FORECAST_CULTIVATION           = "area";
    public static final String FORECAST_SOWING_DATE           = "crop_showing_date";
    public static final String FORECAST_ENTRY_DATE            = "date";
    public static final String FORECAST_ENTRY_TIME            = "time";
    public static final String FORECAST_USER_ID               = "uid";
    public static final String VILLAGE_NAME               = "village_name";

    public static final String  HARVEST_FORCASTING_QUERY ="CREATE TABLE "
            + HARVEST_FORCASTING_TABLE + "(" + ID_CL
            + " INTEGER PRIMARY KEY AUTOINCREMENT ," +FORECAST_FARM_ID
            + " TEXT, "+ FORECAST_PLANT_SEED
            + " TEXT, "+ FORECAST_SOWING_KG
            + " TEXT, "+ FORECAST_CULTIVATION
            + " TEXT, "+ FORECAST_SOWING_DATE
            + " TEXT, "+ FORECAST_ENTRY_DATE
            + " TEXT, "+ FORECAST_ENTRY_TIME
            + " TEXT, "+ FORECAST_USER_ID
            + " TEXT, "+ VILLAGE_NAME
            + " TEXT," + STATUS
            + " TEXT);";


    // below variable is for our table name.
    public static final String FARM_VILLAGE_SQL_TABLE = "com_farm_village_sql";

    // below variable is for our id column.

    public static final String SQL_FARM_DETAIL_ID           = "id";
    public static final String SQL_VILLAGE_ID               = "village_id";
    public static final String SQL_VILLAGE_NAME             = "village_name";
    public static final String SQL_FARM_NAME                = "farm_name";
    public static final String SQL_FARM_ID                  = "farm_id";
    public static final String SQL_FARM_NO                  = "farm_no";


    public static final String  FARM_VILLAGE_SQL_QUERY ="CREATE TABLE "
            + FARM_VILLAGE_SQL_TABLE + "(" + SQL_FARM_DETAIL_ID
            + " TEXT ," + SQL_VILLAGE_ID
            + " TEXT, "+ SQL_VILLAGE_NAME
            + " TEXT, "+ SQL_FARM_NAME
            + " TEXT, "+ SQL_FARM_ID
            + " TEXT, "+ SQL_FARM_NO
            + " TEXT," + STATUS
            + " TEXT);";

    public static final String FARM_FLORA_SQL_TABLE = "com_flora";

    public static final String SQL_PLANT_ID         = "id";
    public static final String SQL_PLANT_NAME       = "flora_name";

    public static final String FARM_FLORA_SQL_QUERY = "CREATE TABLE "
            + FARM_FLORA_SQL_TABLE + "(" + SQL_PLANT_ID
            + " TEXT ," +SQL_PLANT_NAME
            + " TEXT);";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HARVEST_QUERY);
        db.execSQL(HARVEST_FORCASTING_QUERY);
        db.execSQL(FARM_VILLAGE_SQL_QUERY);
        db.execSQL(FARM_FLORA_SQL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + HARVEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HARVEST_FORCASTING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FARM_VILLAGE_SQL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FARM_FLORA_SQL_TABLE);
        onCreate(db);
    }
}
