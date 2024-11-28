package com.sage.cems.models;

public class ShortAnswer extends Question{

    private String correctAnswer;
    private String studentAnswer;

    public ShortAnswer(){}

    public ShortAnswer(String correctAnswer, String studentAnswer) {
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    public ShortAnswer(int index, String statement, String correctAnswer, String studentAnswer) {
        super(index, statement);
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
