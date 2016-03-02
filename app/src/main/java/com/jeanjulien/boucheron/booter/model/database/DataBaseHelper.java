package com.jeanjulien.boucheron.booter.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by JBOUCHER on 27/01/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "booter.db";
    public static final String COL_SEPARATOR = ",";
    public static final String TEXT_TYPE = " TEXT ";
    public static final String INTEGER_TYPE = " INTEGER ";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(ComputerLoader.ComputerEntry.getCreationString());
        db.execSQL(NetworkLoader.NetworkEntry.getCreationString());

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(ComputerLoader.ComputerEntry.getDeletionString());
        db.execSQL(NetworkLoader.NetworkEntry.getDeletionString());
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public Cursor getDataList(String tableName, String[] projection) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                tableName,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return cursor;
    }

    public Cursor getDataList(String tableName, String[] projection,String whereCriteria, String[] values) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(
                tableName,  // The table to query
                projection,                               // The columns to return
                whereCriteria   ,                                // The columns for the WHERE clause
                values,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return cursor;
    }

}
