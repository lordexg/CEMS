package com.sage.cems.models;

import java.util.ArrayList;
import java.util.List;

public class Course {

    String courseID;
    String courseName;
    List<Exam> exams;

    public Course(){}

    public Course(String courseID, String courseName, List<Exam> exams) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.exams = exams;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
