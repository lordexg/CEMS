package com.sage.cems.models;

public class Recorrection {
    private String RECORRECTION_ID;
    private String examID;
    private String studentID;
    private String studentMessage;
    private boolean requestStatus;

    public Recorrection(){}

    public Recorrection(String RECORRECTION_ID, String examID, String studentID, String studentMessage, boolean requestStatus) {
        this.RECORRECTION_ID = RECORRECTION_ID;
        this.examID = examID;
        this.studentID = studentID;
        this.studentMessage = studentMessage;
        this.requestStatus = requestStatus;
    }

    public String getRECORRECTION_ID() {
        return RECORRECTION_ID;
    }

    public void setRECORRECTION_ID(String RECORRECTION_ID) {
        this.RECORRECTION_ID = RECORRECTION_ID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentMessage() {
        return studentMessage;
    }

    public void setStudentMessage(String studentMessage) {
        this.studentMessage = studentMessage;
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }
}
