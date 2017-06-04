package com.example.piotr.lab5;

import com.example.piotr.lab5.model.ProjectGroup;
import com.example.piotr.lab5.model.Student;

import java.util.List;

public interface DataManager {
    Student getStudent(long id);
    List<Student> getStudents();
    long saveStudent(Student student);
    boolean deleteStudent(Student student);

    ProjectGroup getProjectGroup(long id);
    List<ProjectGroup> getProjectGroups();
    long saveProjectGroup(ProjectGroup group);
    void deleteProjectGroup(ProjectGroup group);
}
