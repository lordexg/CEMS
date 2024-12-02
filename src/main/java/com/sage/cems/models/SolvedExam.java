package com.sage.cems.models;

import java.util.List;

public class SolvedExam {
    private String studentID;
    private String courseID;
    private String examID;
    private List<String> solvedExamAnswers;

    public SolvedExam(String studentID, String courseID, String examID, List<String> solvedExamAnswers) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.examID = examID;
        this.solvedExamAnswers = solvedExamAnswers;
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

    public List<String> getSolvedExamAnswers() {
        return solvedExamAnswers;
    }

    public void setSolvedExamAnswers(List<String> solvedExamAnswers) {
        this.solvedExamAnswers = solvedExamAnswers;
    }
}
