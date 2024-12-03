package com.sage.cems.models;

import java.util.List;
import java.util.Date;

public class Exam {
    private int examLength;
    private String exam_ID; // unique throughout the system not in its course only
    private String courseID;
    private long examDuration;
    private Date examStartDate;
    private String examName;
    private Double fullMark;
    private boolean approved;
    private boolean completed;
    private List<Question> questions;

    public Exam(){}


    public Exam(int examLength, String courseID, long examDuration, Date examStartDate, String examName,
                Double fullMark, boolean approved, List<Question> questions, boolean completed) {
        this.examLength = examLength;
        this.examDuration = examDuration;
        this.examStartDate = examStartDate;
        this.examName = examName;
        this.fullMark = fullMark;
        this.approved = approved;
        this.questions = questions;
        this.courseID = courseID;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getExamLength() {
        return examLength;
    }

    public void setExamLength(int examLength) {
        this.examLength = examLength;
    }

    public String getExam_ID() {
        return exam_ID;
    }

    public void setExam_ID(String exam_ID) {
        this.exam_ID = exam_ID;
    }

    public long getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(long examDuration) {
        this.examDuration = examDuration;
    }

    public Date getExamStartDate() {
        return examStartDate;
    }

    public void setExamStartDate(Date examStartDate) {
        this.examStartDate = examStartDate;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public double getFullMark() {
        return fullMark;
    }

    public void setFullMark(Double fullMark) {
        this.fullMark = fullMark;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
