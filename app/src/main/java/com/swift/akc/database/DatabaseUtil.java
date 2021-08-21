package com.swift.akc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import static com.swift.akc.database.DatabaseHelper.SQL_FARM_ID;

public class DatabaseUtil {

    public static final String[] MY_HARVEST_FIELD = {

            DatabaseHelper.FLORA_ST_ID,
            DatabaseHelper.DTM,
            DatabaseHelper.EDTM,
            DatabaseHelper.ENTRY_DATE,
            DatabaseHelper.EUID,
            DatabaseHelper.FARM_ID,
            DatabaseHelper.ISSUE_BY,
            DatabaseHelper.SOWING_DATE,
            DatabaseHelper.PLANT_GROUP_ID,
            DatabaseHelper.PLANTSEED,
            DatabaseHelper.SAPPLING_QUANTITY,
            DatabaseHelper.PROJECT_ID,
            DatabaseHelper.VERSION,
            DatabaseHelper.UID,
            DatabaseHelper.VLG_ID,
            DatabaseHelper.SOLD_QUANTITY,
            DatabaseHelper.HARVEST_DATE,
            DatabaseHelper.HARVEST_QUANTITY,
            DatabaseHelper.OWN_HOME_USE,
            DatabaseHelper.SOLD_RATE,
            DatabaseHelper.TOTAL_INCOME,
            DatabaseHelper.STATUS,
            DatabaseHelper.FLORA_ST_ID_M,


    };

    public static final String[] MY_HARVEST_FORECASTING_FIELD ={
            DatabaseHelper.ID_CL,
            DatabaseHelper.FORECAST_FARM_ID,
            DatabaseHelper.FORECAST_PLANT_SEED ,
            DatabaseHelper.FORECAST_SOWING_KG,
            DatabaseHelper.FORECAST_CULTIVATION,
            DatabaseHelper.FORECAST_SOWING_DATE,
            DatabaseHelper.FORECAST_ENTRY_DATE,
            DatabaseHelper.FORECAST_ENTRY_TIME,
            DatabaseHelper.FORECAST_USER_ID,
            DatabaseHelper.VILLAGE_NAME,
            DatabaseHelper.STATUS

    };

    public static final String[] FARM_VILLAGE_SQL_TABLE_FIELD={

            DatabaseHelper.SQL_FARM_DETAIL_ID,
            DatabaseHelper.SQL_VILLAGE_ID,
            DatabaseHelper.SQL_VILLAGE_NAME,
            DatabaseHelper.SQL_FARM_NAME,
            DatabaseHelper.SQL_FARM_NO,
            DatabaseHelper.SQL_FARM_ID,
            DatabaseHelper.STATUS
    };

    public static final  String[] FARM_FLORA_SQL_TABLE_FIELD={
            DatabaseHelper.SQL_PLANT_ID,
            DatabaseHelper.SQL_PLANT_NAME
    };




    static SQLiteDatabase db;
    Context context;
    ContentValues values;
    DatabaseHelper databaseHelper;

    public DatabaseUtil(Context context){
        this.context = context;
        values = new ContentValues();
    }

    public SQLiteDatabase open(){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        db = databaseHelper.getWritableDatabase();
        return db;
    }

    public void close(){
        if(databaseHelper != null){
            db.close();
        }
    }

    public long addHarvest(int floraId, String strsowingDate, String strsapQuantity, String strharvestDate, String strharvestQuantity,
                           String strownUseQuantity, String strsoldQuantity,
                           String strsoldRate, String strtotalIncome, String strfarmId, String currentdate) {
        values.clear();
        values.put(DatabaseHelper.FARM_ID,strfarmId);
        values.put(DatabaseHelper.DTM,currentdate);
        values.put(DatabaseHelper.PLANTSEED,floraId);
        values.put(DatabaseHelper.SOWING_DATE,strsowingDate);
        values.put(DatabaseHelper.ISSUE_BY,1);
        values.put(DatabaseHelper.SAPPLING_QUANTITY,strsapQuantity);
        values.put(DatabaseHelper.EUID,0);
        values.put(DatabaseHelper.EDTM,"");
        values.put(DatabaseHelper.ENTRY_DATE,currentdate);
        values.put(DatabaseHelper.VLG_ID,1);
        values.put(DatabaseHelper.PLANT_GROUP_ID,2);
        values.put(DatabaseHelper.VERSION,1);
        values.put(DatabaseHelper.SOLD_QUANTITY,strsoldQuantity);
        values.put(DatabaseHelper.HARVEST_DATE,strharvestDate);
        values.put(DatabaseHelper.HARVEST_QUANTITY,strharvestQuantity);
        values.put(DatabaseHelper.OWN_HOME_USE,strownUseQuantity);
        values.put(DatabaseHelper.SOLD_RATE,strsoldRate);
        values.put(DatabaseHelper.TOTAL_INCOME,strtotalIncome);
        values.put(DatabaseHelper.FLORA_ST_ID_M,0);
        values.put(DatabaseHelper.STATUS,0);

       return db.insert(DatabaseHelper.HARVEST_TABLE,null,values);



    }

    public Cursor getHarvest() {

        return db.query(DatabaseHelper.HARVEST_TABLE,MY_HARVEST_FIELD,null,null,null,null,null);


    }

    public long addHarvestForcasting(int plantId, String seeds, String area,
                                     String cropShowingDate, int farmId, String date,
                                     String time, String currentDateTimeString, String status) {
           values.clear();
           values.put(DatabaseHelper.FORECAST_FARM_ID,farmId);
           values.put(DatabaseHelper.FORECAST_CULTIVATION,area);
           values.put(DatabaseHelper.FORECAST_PLANT_SEED,plantId);
           values.put(DatabaseHelper.FORECAST_ENTRY_DATE,date);
           values.put(DatabaseHelper.FORECAST_ENTRY_TIME,time);
           values.put(DatabaseHelper.FORECAST_SOWING_DATE,cropShowingDate);
           values.put(DatabaseHelper.FORECAST_SOWING_KG,seeds);
           values.put(DatabaseHelper.STATUS,status);
           return db.insert(DatabaseHelper.HARVEST_FORCASTING_TABLE,null,values);
        }

    public Cursor getHarvestVisitList(){
        return db.query(DatabaseHelper.HARVEST_TABLE,MY_HARVEST_FIELD,null,null,null,null,null);
    }
    public Cursor getHarvestForcasting() {
        return db.query(DatabaseHelper.HARVEST_FORCASTING_TABLE,MY_HARVEST_FORECASTING_FIELD,null,null,null,null,null);
    }



    public int updateHarvestForecasting(int plantId, String s) {
        values.clear();
        values.put(DatabaseHelper.STATUS,s);

        return db.update(DatabaseHelper.HARVEST_FORCASTING_TABLE,values,
                DatabaseHelper.FORECAST_PLANT_SEED +"='" + plantId +"'", null);
    }

    public int updateHarevest(int floraId) {
        values.clear();
        values.put(DatabaseHelper.STATUS,"1");

        return db.update(DatabaseHelper.HARVEST_TABLE,values,
                DatabaseHelper.PLANTSEED +"='" + floraId +"'", null);

    }

    public long addFarmVillageDetails(String strVillageName, int strVillageId, String strFarmno,
                                      int strFarmId, String strFarmName, int strFarmDetailId) {
        values.clear();
        values.put(DatabaseHelper.SQL_FARM_DETAIL_ID,strFarmDetailId);
        values.put(DatabaseHelper.SQL_FARM_NAME,strFarmName);
        values.put(DatabaseHelper.SQL_FARM_ID,strFarmId);
        values.put(DatabaseHelper.SQL_FARM_NO,strFarmno);
        values.put(DatabaseHelper.SQL_VILLAGE_NAME,strVillageName);
        values.put(DatabaseHelper.SQL_VILLAGE_ID,strVillageId);
        return db.insert(DatabaseHelper.FARM_VILLAGE_SQL_TABLE,null,values);
    }

    public boolean hasFarmVD(int strFarmId, int strFarmDetailId) {

        String abc = SQL_FARM_ID + "='" + strFarmId +"'" + " AND " +
                DatabaseHelper.SQL_FARM_DETAIL_ID + "='" + strFarmDetailId +"'";

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHelper.FARM_VILLAGE_SQL_TABLE +
                " WHERE " + abc, null);

        if(cursor.getCount() <=0){
            cursor.close();
            return false;

        }

        cursor.close();

        return true;
    }

    public long addPlantDetails(int strPlantId, String strPlantName) {

        values.clear();
        values.put(DatabaseHelper.SQL_PLANT_ID,strPlantId);
        values.put(DatabaseHelper.SQL_PLANT_NAME,strPlantName);
        return db.insert(DatabaseHelper.FARM_FLORA_SQL_TABLE,null,values);
    }

    public boolean hasPlantVD(int strPlantId) {
        String abc = DatabaseHelper.SQL_PLANT_ID + "='" + strPlantId +"'";

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHelper.FARM_FLORA_SQL_TABLE +
                " WHERE " + abc, null);

        if(cursor.getCount() <=0){
            cursor.close();
            return false;

        }

        cursor.close();

        return true;
    }

    public Cursor getFarmId(String strfarmno) {
        String abc = DatabaseHelper.SQL_FARM_NO + "='" + strfarmno +"'";
        return db.query(DatabaseHelper.FARM_VILLAGE_SQL_TABLE,FARM_VILLAGE_SQL_TABLE_FIELD,abc,null,
                null,null,null);
    }

    public List<String> getPlantseed() {


            List<String> mypslist = new ArrayList<>();

            String mypsquery = "SELECT * FROM " + DatabaseHelper.FARM_FLORA_SQL_TABLE;

            Cursor cur = db.rawQuery(mypsquery,null);
            if(cur.moveToFirst()){
                do{
                    mypslist.add(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SQL_PLANT_NAME)));
                } while (cur.moveToNext());
            } cur.close();
            return mypslist;

    }

    public Cursor getPlantseedbyName(String item) {
        String abc = DatabaseHelper.SQL_PLANT_NAME + "='" + item +"'";
        return db.query(DatabaseHelper.FARM_FLORA_SQL_TABLE,FARM_FLORA_SQL_TABLE_FIELD,abc,null,
                null,null,null);
    }

    public Cursor getFarmbyId(String strfarmno) {
        String abc = DatabaseHelper.SQL_FARM_ID + "='" + strfarmno +"'";
        return db.query(DatabaseHelper.FARM_VILLAGE_SQL_TABLE,FARM_VILLAGE_SQL_TABLE_FIELD,abc,null,
                null,null,null);
    }

    public Cursor getPlantseebydid(String item) {
        String abc = DatabaseHelper.SQL_PLANT_ID + "='" + item +"'";
        return db.query(DatabaseHelper.FARM_FLORA_SQL_TABLE,FARM_FLORA_SQL_TABLE_FIELD,abc,null,
                null,null,null);
    }

    public Cursor getHarvestVisitListbyStatus(){

        String abc = DatabaseHelper.STATUS + "='" + "0" +"'";
        return db.query(DatabaseHelper.HARVEST_TABLE,MY_HARVEST_FIELD,abc,null,
                null,null,null);


    }

    public Cursor getHarvestForcastingbystatus() {
        String abc = DatabaseHelper.STATUS + "='" + "0" +"'";

        return db.query(DatabaseHelper.HARVEST_FORCASTING_TABLE,MY_HARVEST_FORECASTING_FIELD,abc,null,null,null,null);
    }


}