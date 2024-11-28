package com.sage.cems.models;

public class Question {

    private int index;
    private String statement;

    public Question(){}
    public Question(int index, String statement) {
        this.index = index;
        this.statement = statement;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
