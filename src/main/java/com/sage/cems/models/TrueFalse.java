package com.sage.cems.models;

public class TrueFalse extends Question{

    private boolean correctAnswer;
    private boolean studentAnswer;

    public TrueFalse(){}

    public TrueFalse(boolean correctAnswer, boolean studentAnswer) {
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    public TrueFalse(int index, String statement, boolean correctAnswer, boolean studentAnswer) {
        super(index, statement);
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(boolean studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
