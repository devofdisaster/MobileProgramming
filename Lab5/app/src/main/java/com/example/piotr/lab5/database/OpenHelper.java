package com.example.piotr.lab5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.piotr.lab5.R;
import com.example.piotr.lab5.database.dao.ProjectGroupDAO;
import com.example.piotr.lab5.database.dao.StudentDAO;
import com.example.piotr.lab5.database.table.ProjectGroupTable;
import com.example.piotr.lab5.database.table.StudentProjectGroupTable;
import com.example.piotr.lab5.database.table.StudentTable;
import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;


public class OpenHelper extends SQLiteOpenHelper {

    private Context context;

    public OpenHelper(Context context) {
        super(context, "lab5", null, SQLiteDataManager.dbversion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createStudentTable(db);
        createGroupTable(db);
        StudentProjectGroupTable.onCreate(db);
    }

    private void createGroupTable(SQLiteDatabase db) {
        ProjectGroupTable.onCreate(db);
        ProjectGroupDAO projectGroupDAO= new ProjectGroupDAO(db);
        String[] groups = context.getResources().getStringArray(R.array.projectgroups);

        for (String group : groups) {
            projectGroupDAO.save(new ProjectGroup(group));
        }

    }

    private void createStudentTable(SQLiteDatabase db) {
        StudentTable.onCreate(db);
        StudentDAO studentDAO = new StudentDAO(db);
        String[] firstNames = context.getResources().getStringArray(R.array.firstnames);
        String[] lastNames = context.getResources().getStringArray(R.array.lastnames);

        for (int i = 0; i < Math.min(firstNames.length, lastNames.length); i++) {
            studentDAO.save(new Student(firstNames[i], lastNames[i]));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StudentProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
        ProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
        StudentTable.onUpgrade(db, oldVersion, newVersion);
    }
}
