package com.example.piotr.lab5.database.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ProjectGroupTable {
    public static final String TABLE_NAME = "project_groups";

    public static final class ProjectGroupColumns implements BaseColumns {
        public static final String NAME = "name";
    }

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
                "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY," +
                        "%s TEXT NOT NULL" +
                        ");",
                ProjectGroupTable.TABLE_NAME,
                BaseColumns._ID,
                ProjectGroupColumns.NAME
        ));
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", ProjectGroupTable.TABLE_NAME));
        StudentTable.onCreate(db);
    }
}
