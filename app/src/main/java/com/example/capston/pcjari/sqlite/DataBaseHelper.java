package com.example.capston.pcjari.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * Created by KangSeungho on 2017-11-29.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pcjari.db";
    private static final int DATABASE_VERSION = 6;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseTables.CreateDB_favorite._CREATE_FAVORITE);
        db.execSQL(DataBaseTables.CreateDB_setting._CREATE_SETTING);
        db.execSQL("INSERT INTO " + DataBaseTables.CreateDB_setting._TABLENAME + " VALUES(0, 0, 5);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DataBaseTables.CreateDB_favorite._TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS "+DataBaseTables.CreateDB_setting._TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS JUSO");
        onCreate(db);
    }
}
