package com.example.piotr.lab5.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.piotr.lab5.database.table.ProjectGroupTable;
import com.example.piotr.lab5.database.table.StudentProjectGroupTable;
import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;

public class StudentProjectGroupDAO {
    private static final String EXISTS_STMT = String.format(
            "SELECT count(*) FROM %s WHERE %s = ? AND %s = ?",
            StudentProjectGroupTable.TABLE_NAME,
            StudentProjectGroupTable.StudentGroupColumns.STUDENT_ID,
            StudentProjectGroupTable.StudentGroupColumns.GROUP_ID
    );

    private static final String INSERT_STMT = String.format(
            "INSERT INTO %s (%s, %s) VALUES (?, ?)",
            StudentProjectGroupTable.TABLE_NAME,
            StudentProjectGroupTable.StudentGroupColumns.STUDENT_ID,
            StudentProjectGroupTable.StudentGroupColumns.GROUP_ID
    );

    private static final String SELECT_STMT = String.format(
            "SELECT g.* FROM %s AS g INNER JOIN %s AS sg on g.%s = sg.%s " +
                    "WHERE sg.%s = ?",
            ProjectGroupTable.TABLE_NAME,
            StudentProjectGroupTable.TABLE_NAME,
            BaseColumns._ID,
            StudentProjectGroupTable.StudentGroupColumns.GROUP_ID,
            StudentProjectGroupTable.StudentGroupColumns.STUDENT_ID
    );

    private SQLiteDatabase db;
    private SQLiteStatement existsStatement;
    private SQLiteStatement insertStatement;

    public StudentProjectGroupDAO(SQLiteDatabase db) {
        this.db = db;
        existsStatement = db.compileStatement(StudentProjectGroupDAO.EXISTS_STMT);
        insertStatement = db.compileStatement(StudentProjectGroupDAO.INSERT_STMT);
    }

    public boolean relationExists(long studentId, long groupId) {
        existsStatement.clearBindings();
        existsStatement.bindLong(1, studentId);
        existsStatement.bindLong(2, groupId);

        return existsStatement.simpleQueryForLong() > 0;
    }

    public void saveRelation(long studentId, long groupId) {
        insertStatement.clearBindings();
        insertStatement.bindLong(1, studentId);
        insertStatement.bindLong(2, groupId);
        insertStatement.executeInsert();
    }

    public void deleteRelation(long studentId, long groupId) {
        db.delete(
                StudentProjectGroupTable.TABLE_NAME,
                String.format(
                        "%s = ? AND %s = ?",
                        StudentProjectGroupTable.StudentGroupColumns.STUDENT_ID,
                        StudentProjectGroupTable.StudentGroupColumns.GROUP_ID
                ),
                new String[] { String.valueOf(studentId), String.valueOf(groupId) }
        );
    }

    public ArrayList<ProjectGroup> getProjectGroupsFor(Student student) {
        ArrayList<ProjectGroup> groups = new ArrayList<>();
        Cursor cur = db.rawQuery(SELECT_STMT, new String[] { String.valueOf(student.getId()) });

        if (cur.moveToFirst()) {
            while (!cur.isAfterLast()) {
                groups.add(new ProjectGroup(cur.getLong(0), cur.getString(1)));
                cur.moveToNext();
            }
        }

        if (!cur.isClosed()) {
            cur.close();
        }

        return groups;
    }
}
