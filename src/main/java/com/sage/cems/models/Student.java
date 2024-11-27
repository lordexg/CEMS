package com.sage.cems.models;

import com.sage.cems.models.user.User;
import com.sage.cems.util.FileManager;

import java.util.ArrayList;

public class Student extends User {

    ArrayList<Course> courses = new ArrayList<Course>();

//    public Student(String userName, String password, String firstName, String lastName, String email, String phoneNumber, int ID, Role role) {
//        super(userName, password, firstName, lastName, email, phoneNumber, ID, role);
//    }
}
