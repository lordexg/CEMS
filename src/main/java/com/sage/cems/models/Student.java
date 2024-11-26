package com.sage.cems.models;

import com.sage.cems.models.utils.FileManager;

import java.util.List;

public class Student extends User{
    private List<Course> enrolledCourses;

    public Student(FileManager fileManager, String userName, String password, String firstName, String lastName, String email, String phoneNumber) {
        super(fileManager, userName, password, firstName, lastName, email, phoneNumber);
    }

    public List<Course> loadEnrolledCourses() {
        return enrolledCourses;
    }


//- recorrectionRequest(): void
//- viewCompletedExams(): void
}
