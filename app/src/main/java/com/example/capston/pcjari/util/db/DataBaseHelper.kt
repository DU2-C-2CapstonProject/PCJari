package com.example.capston.pcjari.util.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_favorite
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_setting

/**
 * Created by KangSeungho on 2017-11-29.
 */
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CreateDB_favorite._CREATE_FAVORITE)
        db.execSQL(CreateDB_setting._CREATE_SETTING)
        db.execSQL("INSERT INTO " + CreateDB_setting._TABLENAME + " VALUES(0, 0, 5);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CreateDB_favorite._TABLENAME)
        db.execSQL("DROP TABLE IF EXISTS " + CreateDB_setting._TABLENAME)
        db.execSQL("DROP TABLE IF EXISTS JUSO")
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME: String? = "pcjari.db"
        private const val DATABASE_VERSION = 6
    }
}