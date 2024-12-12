package com.sage.cems.services;

import com.sage.cems.daos.ExamDAO;
import com.sage.cems.daos.GradeDAO;
import com.sage.cems.daos.SolvedExamDAO;
import com.sage.cems.models.Exam;
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
    private final ExamDAO examDAO;

    public GradeService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.solvedExamDAO = new SolvedExamDAO(fileManager);
        this.gradeDAO = new GradeDAO(fileManager);
        this.examDAO = new ExamDAO(fileManager);
    }


    public List<Grade> getStudentGrades(String studentID) throws IOException {
        List<Grade> grades = new ArrayList<>();
        for(Grade grade : gradeDAO.getAllGrades(studentID)) {
            if(grade.getStudentID().equals(studentID) && isGradeApproved(grade)){
                grades.add(grade);
            }
        }
        return grades;
    }

    public String getFormatedGrade(String examID, String studentID) throws IOException {
        Grade studentGrade = null;
        for (Grade grade : gradeDAO.getAllGrades(studentID)) {
            if (grade.getStudentID().equals(studentID) && grade.getExamID().equals(examID))
                studentGrade = grade;
        }
        return studentGrade == null ? "" : studentGrade.getMark() + "/" + studentGrade.getFullMark()
                + " (" + getGradeSymbol(studentGrade) + ")";
    }

    public Exam getStudentSolvedExam(String examID, String studentID) throws IOException {
        SolvedExam studentSolvedExam = null;
        for(SolvedExam solvedExam : solvedExamDAO.getAllSolvedExams(examID)){
            if ((solvedExam.getStudentID().equals(studentID)) && (solvedExam.getExamID().equals(examID))){
                studentSolvedExam = solvedExam;
            }
        }
        Exam studentExam = examDAO.getExam(examID);

        if (studentExam == null || studentSolvedExam == null)
            throw new IOException("Student with this id doesn't do the exam with given id");

        for (int i = 0; i < studentExam.getQuestions().size(); ++i) {
            studentExam.getQuestions().get(i).setStudentAnswer(studentSolvedExam.getSolvedExamAnswers().get(i));
        }
        return studentExam;
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

    private boolean isGradeApproved(Grade grade) throws IOException {
        Exam exam = examDAO.getExam(grade.getExamID());
        return exam.isApproved();
    }
}
