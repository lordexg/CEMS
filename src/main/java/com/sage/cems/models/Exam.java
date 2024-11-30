package com.sage.cems.models;

import java.util.List;
import java.util.Date;

public class Exam {
    private int examLength;
    private String exam_ID;
    private Course course;
    private long examDuration;
    private Date examStartDate;
    private String examName;
    private Double mark;
    private boolean approved;
    private List<Question> questions;

    public Exam(){}


    public Exam(int examLength, String exam_ID, Course course, long examDuration, Date examStartDate, String examName,
                Double mark, boolean approved, List<Question> questions) {
        this.examLength = examLength;
        this.course = course;
        this.examDuration = examDuration;
        this.examStartDate = examStartDate;
        this.examName = examName;
        this.mark = mark;
        this.approved = approved;
        this.questions = questions;
        this.exam_ID = exam_ID;
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

    public double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
