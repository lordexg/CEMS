package com.sage.cems.models;

public class Grade {

    private String gradeID;
    private int mark;
    private String studentID;
    private String courseID;
    private String examID;

    public Grade(){}

    public Grade(int mark, String studentID, String courseID, String examID, String gradeID) {
        this.gradeID = gradeID;
        this.mark = mark;
        this.studentID = studentID;
        this.courseID = courseID;
        this.examID = examID;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


}
