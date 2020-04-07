package com.criddam.covid_19criddam.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String id ,String need, String location_local) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID,id);
        contentValue.put(DatabaseHelper.what_u_need, need);
        contentValue.put(DatabaseHelper.location, location_local);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.what_u_need, DatabaseHelper.location };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String need, String locaton_local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper._ID,_id);
        contentValues.put(DatabaseHelper.what_u_need, need);
        contentValues.put(DatabaseHelper.location, locaton_local);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}