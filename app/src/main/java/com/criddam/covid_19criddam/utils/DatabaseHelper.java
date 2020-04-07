package com.criddam.covid_19criddam.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user";
    static final String DB_NAME = "COVIDUSER.DB";
    static final int DB_VERSION = 1;

    public static final String _ID = "_id";
    public static final String what_u_need = "what_u_need";
    public static final String location = "location";


    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + what_u_need + " TEXT NOT NULL, " + location + " TEXT);";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insert(String need, String location_local) {

        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.what_u_need, need);
        contentValue.put(DatabaseHelper.location, location_local);
        long result = db.insert(DatabaseHelper.TABLE_NAME, null, contentValue);

        if(result==-1){
            return false;
        }else {
            return  true;
        }
    }


    public Cursor fetch() {

     SQLiteDatabase db = this.getReadableDatabase();

     String query = "SELECT* FROM "+ TABLE_NAME;
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.what_u_need, DatabaseHelper.location };
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        Cursor data  = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
