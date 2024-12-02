package com.sage.cems.models;

public class Grade {

    private int mark;
    private int fullMark;
    private String studentID;
    private String courseID;
    private String examID;

    public Grade(){}

    public Grade(int mark, int fullMark, String studentID, String courseID, String examID) {
        this.mark = mark;
        this.fullMark = fullMark;
        this.studentID = studentID;
        this.courseID = courseID;
        this.examID = examID;
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

    public int getFullMark() {
        return fullMark;
    }

    public void setFullMark(int fullMark) {
        this.fullMark = fullMark;
    }

    public String calculateGrade(int mark, int fullMark) {
        if (mark < 0 || fullMark <= 0 || mark > fullMark) {
            return "Invalid input";
        }

        double percentage = ((double) mark / fullMark) * 100;

        if (percentage >= 90) {
            return "A";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 60) {
            return "D";
        } else if (percentage >= 50) {
            return "E";
        } else {
            return "F";
        }

    }


}
