package com.sage.cems.models;

import java.util.List;

public class Question {

    private String QuestionID;
    private String examID;
    private String statement;
    private String correctAnswer;
    private String studentAnswer;
    private List<String> choices;
    private QuestionType questionType;

    public Question(){}
    public Question(String QuestionID, String statement) {
        this.QuestionID = QuestionID;
        this.statement = statement;
    }

    public Question(String QuestionID, String examID, String statement, String correctAnswer, QuestionType questionType, String studentAnswer) {
        this.QuestionID = QuestionID;
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.examID = examID;
        this.studentAnswer = studentAnswer;
    }
    public Question(String QuestionID, String examID, String statement, String correctAnswer, List<String> choices, QuestionType questionType) {
        this.QuestionID = QuestionID;
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
        this.questionType = questionType;
        this.examID = examID;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getExamID() {
        return examID;
    }


    public String getQuestionID() {
        return QuestionID;
    }

    public String getStatement() {
        return statement;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getChoices() {
        return choices;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionID(String questionID) {
        this.QuestionID = questionID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
