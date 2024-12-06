package com.sage.cems.services;

import com.sage.cems.daos.ExamDAO;
import com.sage.cems.daos.GradeDAO;
import com.sage.cems.daos.SolvedExamDAO;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.models.SolvedExam;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeService {

    private final SolvedExamDAO solvedExamDAO;
    private final GradeDAO gradeDAO;
    private final ExamDAO examDAO;

    public GradeService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.solvedExamDAO = new SolvedExamDAO(fileManager);
        this.gradeDAO = new GradeDAO(fileManager);
        this.examDAO = new ExamDAO(fileManager);
    }


    public List<Grade> getStudentGrades(String studentID) throws IOException {
        List<Grade> gradesList = new ArrayList<>();
        List<Grade> grades;
        grades = gradeDAO.getAllGrades(studentID);
        for(Grade grade : grades){
            if(grade.getStudentID().equals(studentID)){
                gradesList.add(grade);
            }
        }
        return gradesList;
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

    public String getGradeSymbol(Grade grade) throws IOException {
        Exam exam;
        for (Exam Exam : examDAO.getAllExams(grade.getExamID())){
            if(Exam.getExam_ID().equals(grade.getExamID())){
                exam = Exam;
                return calculateGrade(grade.getMark(),(int)exam.getFullMark());
            }
        }
        return null;
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
        } else if (percentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

}
