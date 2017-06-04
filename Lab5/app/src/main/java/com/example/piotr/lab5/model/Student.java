package com.example.piotr.lab5.model;

import java.util.ArrayList;

public class Student {
    private long id;
    private String firstname;
    private String lastname;
    private ArrayList<ProjectGroup> projectGroups = new ArrayList<>();

    public Student(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Student(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ArrayList<ProjectGroup> getProjectGroups() {
        return projectGroups;
    }

    public void addProjectGroup(ProjectGroup group) {
        this.projectGroups.add(group);
    }

    public boolean isInProjectGroups() {
        return getProjectGroups().size() > 0;
    }

    public String toString() {
        return firstname + ' ' + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass())  {
            return false;
        }

        Student student = (Student) o;

        if (id != student.id) {
            return false;
        }

        if (!firstname.equals(student.firstname)) {
            return false;
        }

        return lastname.equals(student.lastname);

    }

    @Override
    public int hashCode() {
        int result = (int) id;

        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();

        return result;
    }
}
