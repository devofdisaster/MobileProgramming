package com.example.piotr.lab5.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.piotr.lab5.DAO;
import com.example.piotr.lab5.database.table.StudentTable;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;

public class StudentDAO implements DAO<Student> {

    private static final String INSERT_STMT = String.format(
            "INSERT INTO %s (%s, %s) VALUES (?, ?)",
            StudentTable.TABLE_NAME,
            StudentTable.StudentColumns.FIRSTNAME,
            StudentTable.StudentColumns.LASTNAME
    );

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public StudentDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(StudentDAO.INSERT_STMT);
    }

    @Override
    public long save(Student student) {
        insertStatement.clearBindings();
        insertStatement.bindAllArgsAsStrings(new String[] {
                student.getFirstname(),
                student.getLastname()
        });

        return insertStatement.executeInsert();
    }

    @Override
    public void update(Student student) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(StudentTable.StudentColumns.FIRSTNAME, student.getFirstname());
        contentValues.put(StudentTable.StudentColumns.LASTNAME, student.getLastname());

        db.update(
                StudentTable.TABLE_NAME,
                contentValues,
                String.format("%s = ?", BaseColumns._ID),
                new String[] { String.valueOf(student.getId()) }
        );
    }

    @Override
    public void delete(Student student) {
        if (student.getId() > 0) {
            db.delete(
                    StudentTable.TABLE_NAME,
                    String.format("%s = ?", BaseColumns._ID),
                    new String[] { String.valueOf(student.getId()) }
            );
        }
    }

    @Override
    public Student get(long id) {
        Student student = null;
        Cursor cur = db.query(
                StudentTable.TABLE_NAME,
                null,
                String.format("%s = ?", BaseColumns._ID),
                new String[] { String.valueOf(id) },
                null,
                null,
                null
        );

        if (cur.moveToFirst()) {
            student = new Student(cur.getLong(0), cur.getString(1), cur.getString(2));
        }

        if (!cur.isClosed()) {
            cur.close();
        }

        return student;
    }

    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> list = new ArrayList<>();
        Cursor cur = db.query(
                StudentTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                BaseColumns._ID
        );

        if (cur.moveToFirst()) {
            while (!cur.isAfterLast()) {
                list.add(new Student(cur.getLong(0), cur.getString(1), cur.getString(2)));
                cur.moveToNext();
            }
        }

        if (!cur.isClosed()) {
            cur.close();
        }

        return list;
    }
}
