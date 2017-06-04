package com.example.piotr.lab5.database.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class StudentTable {
    public static final String TABLE_NAME = "students";

    public static final class StudentColumns implements BaseColumns {
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
    }

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
            "CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY," +
                "%s TEXT NOT NULL," +
                "%s TEXT NOT NULL" +
            ");",
            StudentTable.TABLE_NAME,
            BaseColumns._ID,
            StudentColumns.FIRSTNAME,
            StudentColumns.LASTNAME
        ));
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", StudentTable.TABLE_NAME));
        StudentTable.onCreate(db);
    }
}
