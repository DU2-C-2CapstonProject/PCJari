package com.example.capston.pcjari.sqlite

import android.provider.BaseColumns

/**
 * Created by KangSeungho on 2017-11-29.
 */
class DataBaseTables {
    // 즐겨찾기 테이블
    object CreateDB_favorite : BaseColumns {
        val _TABLENAME: String? = "favorite"
        val _CREATE_FAVORITE: String? = ("CREATE TABLE " + _TABLENAME + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY);")
    }

    // 설정 테이블
    object CreateDB_setting : BaseColumns {
        val _TABLENAME: String? = "setting"
        val FIRST_ACTIVITY: String? = "first_activity"
        val DIST: String? = "dist"
        val _CREATE_SETTING: String? = ("CREATE TABLE " + _TABLENAME + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                + FIRST_ACTIVITY + " INTEGER, "
                + DIST + " DOUBLE);")
    }
}