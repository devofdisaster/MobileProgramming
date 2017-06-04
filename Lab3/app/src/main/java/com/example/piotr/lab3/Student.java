package com.example.piotr.lab3;

public class Student {
    private String firstname;
    private String lastname;

    public Student(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String toString() {
        return firstname + ' ' + lastname;
    }
}
