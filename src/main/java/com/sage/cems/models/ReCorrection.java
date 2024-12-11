package com.sage.cems.models;

public class ReCorrection {
    private String reCorrectionID;
    private String examID;
    private String studentID;
    private String studentMessage;
    private boolean requestStatus;

    public ReCorrection(){}

    public ReCorrection(String reCorrectionID, String examID, String studentID, String studentMessage, boolean requestStatus) {
        this.reCorrectionID = reCorrectionID;
        this.examID = examID;
        this.studentID = studentID;
        this.studentMessage = studentMessage;
        this.requestStatus = requestStatus;
    }

    public String getReCorrectionID() {
        return reCorrectionID;
    }

    public void setReCorrectionID(String reCorrectionID) {
        this.reCorrectionID = reCorrectionID;
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
