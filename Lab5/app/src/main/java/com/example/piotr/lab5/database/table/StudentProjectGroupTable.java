package com.example.piotr.lab5.database.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class StudentProjectGroupTable {
    public static final String TABLE_NAME = "students_project_groups";

    public static final class StudentGroupColumns {
        public static final String STUDENT_ID = "student_id";
        public static final String GROUP_ID = "project_group_id";
    }

    public static void onCreate(SQLiteDatabase db) {
        String columnSchema = String.format(
                "%s INTEGER NOT NULL, %s INTEGER NOT NULL",
                StudentGroupColumns.STUDENT_ID,
                StudentGroupColumns.GROUP_ID
        );
        String foreignKeys = String.format(
                "FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s)",
                StudentGroupColumns.STUDENT_ID,
                StudentTable.TABLE_NAME,
                BaseColumns._ID,
                StudentGroupColumns.GROUP_ID,
                ProjectGroupTable.TABLE_NAME,
                BaseColumns._ID
        );
        String primaryKey = String.format(
                "PRIMARY KEY (%s, %s)",
                StudentGroupColumns.STUDENT_ID,
                StudentGroupColumns.GROUP_ID
        );

        db.execSQL(String.format(
                "CREATE TABLE %s (%s, %s, %s);",
                StudentProjectGroupTable.TABLE_NAME,
                columnSchema,
                foreignKeys,
                primaryKey
        ));
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", StudentProjectGroupTable.TABLE_NAME));
        StudentTable.onCreate(db);
    }
}
