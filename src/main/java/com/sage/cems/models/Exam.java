package com.sage.cems.models;

import java.util.List;
import java.util.Date;

public class Exam {
    private int examLength;
    private Course course;
    private Date examDuration;
    private Date examStartDate;
    private String examName;
    private double mark;
    private boolean completed;
    private boolean approved;
    private List<Question> questions;

    public Exam(){}

    public Exam(int examLength, Course course, Date examDuration, Date examStartDate, String examName,
                double mark, boolean completed, boolean approved, List<Question> questions) {
        this.examLength = examLength;
        this.course = course;
        this.examDuration = examDuration;
        this.examStartDate = examStartDate;
        this.examName = examName;
        this.mark = mark;
        this.completed = completed;
        this.approved = approved;
        this.questions = questions;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(Date examDuration) {
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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getExamLength() {
        return examLength;
    }

    public void setExamLength(int examLength) {
        this.examLength = examLength;
    }
}
