package com.sage.cems.models;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private List<Course> courses;

    public Student() {}

    public Student(String password, String firstName, String lastName,
                   String email, String phoneNumber, Role role, List<Course> courses) {
        super(password, firstName, lastName, email, phoneNumber, role);
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
