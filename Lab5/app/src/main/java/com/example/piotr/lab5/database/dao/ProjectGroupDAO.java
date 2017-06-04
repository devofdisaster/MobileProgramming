package com.example.piotr.lab5.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.piotr.lab5.DAO;
import com.example.piotr.lab5.database.table.ProjectGroupTable;
import com.example.piotr.lab5.model.ProjectGroup;

import java.util.ArrayList;


public class ProjectGroupDAO implements DAO<ProjectGroup> {

    private static final String INSERT_STMT = String.format(
            "INSERT INTO %s (%s) VALUES (?)",
            ProjectGroupTable.TABLE_NAME,
            ProjectGroupTable.ProjectGroupColumns.NAME
    );

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public ProjectGroupDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(ProjectGroupDAO.INSERT_STMT);
    }

    @Override
    public long save(ProjectGroup group) {
        insertStatement.clearBindings();
        insertStatement.bindAllArgsAsStrings(new String[] { group.getName() });

        return insertStatement.executeInsert();
    }

    @Override
    public void update(ProjectGroup group) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(ProjectGroupTable.ProjectGroupColumns.NAME, group.getName());

        db.update(
                ProjectGroupTable.TABLE_NAME,
                contentValues,
                String.format("%s = ?", BaseColumns._ID),
                new String[] { String.valueOf(group.getId()) }
        );
    }

    @Override
    public void delete(ProjectGroup group) {
        if (group.getId() > 0) {
            db.delete(
                    ProjectGroupTable.TABLE_NAME,
                    String.format("%s = ?", BaseColumns._ID),
                    new String[] { String.valueOf(group.getId()) }
            );
        }
    }

    @Override
    public ProjectGroup get(long id) {
        ProjectGroup group = null;
        Cursor cur = db.query(
                ProjectGroupTable.TABLE_NAME,
                null,
                String.format("%s = ?", BaseColumns._ID),
                new String[] { String.valueOf(id) },
                null,
                null,
                null
        );

        if (cur.moveToFirst()) {
            group = new ProjectGroup(cur.getLong(0), cur.getString(1));
        }

        if (!cur.isClosed()) {
            cur.close();
        }

        return group;
    }

    @Override
    public ArrayList<ProjectGroup> getAll() {
        ArrayList<ProjectGroup> list = new ArrayList<>();
        Cursor cur = db.query(
                ProjectGroupTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                BaseColumns._ID
        );

        if (cur.moveToFirst()) {
            while (!cur.isAfterLast()) {
                list.add(new ProjectGroup(cur.getLong(0), cur.getString(1)));
                cur.moveToNext();
            }
        }

        if (!cur.isClosed()) {
            cur.close();
        }

        return list;
    }


}
