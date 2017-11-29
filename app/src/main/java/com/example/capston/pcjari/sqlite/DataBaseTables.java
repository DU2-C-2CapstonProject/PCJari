package com.example.capston.pcjari.sqlite;

import android.provider.BaseColumns;

/**
 * Created by KangSeungho on 2017-11-29.
 */

public final class DataBaseTables {
    // 즐겨찾기 테이블
    public static final class CreateDB_favorite implements BaseColumns {
        public static final String _TABLENAME = "favorite";
        public static final String _CREATE_FAVORITE =
                "CREATE TABLE " + _TABLENAME + "("
                + _ID + " INTEGER PRIMARY KEY);";
    }

    // 설정 테이블
    public static final class CreateDB_setting implements BaseColumns {
        public static final String _TABLENAME = "setting";
        public static final String FIRST_ACTIVITY = "first_activity";
        public static final String _CREATE_SETTING =
                "CREATE TABLE " + _TABLENAME + "("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + FIRST_ACTIVITY + " INTEGER);";
    }

    // 주소 테이블
    public static final class CreateDB_juso implements BaseColumns {
        public static final String _TABLENAME = "juso";
        public static final String SI = "si";
        public static final String GU = "gu";
        public static final String DONG = "dong";
        public static final String _CREATE_JUSO =
                "CREATE TABLE " + _TABLENAME + "("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + SI + " text, "
                        + GU + " text, "
                        + DONG + " text);";
    }
}
