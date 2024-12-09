package com.sage.cems.services;

import com.sage.cems.daos.GradeDAO;
import com.sage.cems.daos.SolvedExamDAO;
import com.sage.cems.models.Grade;
import com.sage.cems.models.SolvedExam;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private final SolvedExamDAO solvedExamDAO;
    private final GradeDAO gradeDAO;

    public GradeService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.solvedExamDAO = new SolvedExamDAO(fileManager);
        this.gradeDAO = new GradeDAO(fileManager);
    }


    public List<Grade> getStudentGrades(String studentID) throws IOException {
        List<Grade> grades = new ArrayList<>();
        for(Grade grade : gradeDAO.getAllGrades(studentID)) {
            if(grade.getStudentID().equals(studentID)){
                grades.add(grade);
            }
        }
        return grades;
    }

    public SolvedExam getStudentSolvedExam(String examID, String studentID) throws IOException {
        List<SolvedExam> solvedExams = solvedExamDAO.getAllSolvedExams(examID);
        for(SolvedExam solvedExam : solvedExams){
            if((solvedExam.getStudentID().equals(studentID)) && (solvedExam.getExamID().equals(examID))){
                return solvedExam;
            }
        }
        return new SolvedExam();
    }

    public String getGradeSymbol(Grade grade) {
        return calculateGrade(grade.getMark(), grade.getFullMark());
    }

    private String calculateGrade(int mark, int fullMark) {
        double percentage = ((double) mark / fullMark) * 100;

        if (percentage >= 95) {
            return "A+";
        } else if (percentage >= 90) {
            return "A";
        } else if (percentage >= 85) {
            return "B+";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 75) {
            return "C+";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 65) {
            return "D+";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

}
