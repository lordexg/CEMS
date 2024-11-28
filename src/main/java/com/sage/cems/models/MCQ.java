package com.sage.cems.models;

import java.util.List;

public class MCQ extends Question{

    private char correctAnswer;
    private char studentAnswer;
    private List<String> choices;

    public MCQ(){}

    public MCQ(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public MCQ(int index, String statement, char correctAnswer) {
        super(index, statement);
        this.correctAnswer = correctAnswer;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public char getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(char studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
