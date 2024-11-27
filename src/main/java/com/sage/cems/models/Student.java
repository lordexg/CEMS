package com.sage.cems.models;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;

import java.util.ArrayList;

public class Student extends User {

    ArrayList<Course> courses;

    // empty student
    public Student() {}

    public Student(String ID, String password, String firstName, String lastName,
                   String email, String phoneNumber, Role role, ArrayList<Course> courses) {
        super(ID, password, firstName, lastName, email, phoneNumber, role);
        this.courses = Course.getCourses(this.getID());
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

}
