package com.example.piotr.lab5.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.piotr.lab5.DataManager;
import com.example.piotr.lab5.database.dao.ProjectGroupDAO;
import com.example.piotr.lab5.database.dao.StudentDAO;
import com.example.piotr.lab5.database.dao.StudentProjectGroupDAO;
import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.ArrayList;


public class SQLiteDataManager implements DataManager {

    public static final int dbversion = 1;

    private SQLiteDatabase db;
    private StudentDAO studentDAO;
    private ProjectGroupDAO projectGroupDAO;
    private StudentProjectGroupDAO studentProjectGroupDAO;

    public SQLiteDataManager(SQLiteOpenHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
        studentProjectGroupDAO = new StudentProjectGroupDAO(db);
        studentDAO = new StudentDAO(db);
        projectGroupDAO = new ProjectGroupDAO(db);
    }

    @Override
    public Student getStudent(long id) {
        Student student = studentDAO.get(id);

        if (student != null) {
            student.getProjectGroups().addAll(getProjectGroupsFor(student));
        }

        return student;
    }

    public ArrayList<ProjectGroup> getProjectGroupsFor(Student student) {
        return studentProjectGroupDAO.getProjectGroupsFor(student);
    }

    @Override
    public ArrayList<Student> getStudents() {
        return studentDAO.getAll();
    }

    @Override
    public long saveStudent(Student student) {
        long studentId = 0;

        try {
            db.beginTransaction();

            if (student.getId() > 0) {
                studentDAO.update(student);
                studentId = student.getId();
            } else {
                studentId = studentDAO.save(student);
            }

            if (student.isInProjectGroups()) {
                for (ProjectGroup group : student.getProjectGroups()) {
                    long groupId = 0;
                    ProjectGroup savedGroup = projectGroupDAO.get(group.getId());

                    groupId = savedGroup == null ? projectGroupDAO.save(group) : savedGroup.getId();

                    if (!studentProjectGroupDAO.relationExists(studentId, groupId)) {
                        studentProjectGroupDAO.saveRelation(studentId, groupId);
                    }
                }
            }

            for (ProjectGroup group : studentProjectGroupDAO.getProjectGroupsFor(student)) {
                if (!student.getProjectGroups().contains(group)) {
                    studentProjectGroupDAO.deleteRelation(studentId, group.getId());
                }
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("SQL exception", "Transaction cancelled while saving student", e);
            studentId = 0;
        } finally {
            db.endTransaction();
        }

        return studentId;
    }

    @Override
    public boolean deleteStudent(Student student) {
        boolean result = false;

        try {
            db.beginTransaction();

            for (ProjectGroup group : student.getProjectGroups()) {
                studentProjectGroupDAO.deleteRelation(student.getId(), group.getId());
            }

            studentDAO.delete(student);
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e("SQL exception", "Transaction cancelled while deleting student", e);
        } finally {
            db.endTransaction();
        }

        return result;
    }

    @Override
    public ProjectGroup getProjectGroup(long id) {
        return projectGroupDAO.get(id);
    }

    @Override
    public ArrayList<ProjectGroup> getProjectGroups() {
        return projectGroupDAO.getAll();
    }

    @Override
    public long saveProjectGroup(ProjectGroup group) {
        return projectGroupDAO.save(group);
    }

    @Override
    public void deleteProjectGroup(ProjectGroup group) {
        projectGroupDAO.delete(group);
    }
}
