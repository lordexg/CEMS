package com.sage.cems.services;

import com.sage.cems.daos.GradeDAO;
import com.sage.cems.daos.SolvedExamDAO;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.models.Question;
import com.sage.cems.models.SolvedExam;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sage.cems.util.TimeConversion.calculateRemainingTimeInSeconds;
import static com.sage.cems.util.TimeConversion.millisecondsToSeconds;

public class ExamService {

    private final SolvedExamDAO solvedExamDAO;
    private final GradeDAO gradeDAO;

    public ExamService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.solvedExamDAO = new SolvedExamDAO(fileManager);
        this.gradeDAO = new GradeDAO(fileManager);
    }

    public void submitExam(Exam exam, String studentID) throws IOException {
        gradeDAO.addGrade(pupulateGrade(exam,studentID));
        addSolvedExam(exam, studentID);
    }

    public boolean isExamCompleted(Exam exam, String studentId) throws IOException {
        List<SolvedExam> solvedExams = solvedExamDAO.getAllSolvedExams(studentId);
        for (SolvedExam solvedExam : solvedExams) {
            if (solvedExam.getExamID().equals(exam.getExam_ID()))
                return true;
        }

        long remainingTime = calculateRemainingTimeInSeconds(exam.getExamStartDate());
        return remainingTime < 0 && Math.abs(remainingTime) > millisecondsToSeconds(exam.getExamDuration());
    }

    private int calculateGrade(Exam exam) {
        int mark = 0;
        for (Question question : exam.getQuestions()) {
            if (question.getStudentAnswer().equals(question.getCorrectAnswer())) {
                mark++;
            }
        }
        return mark;
    }

    private Grade pupulateGrade(Exam exam, String studentID){
        Grade grade = new Grade();
        grade.setMark(calculateGrade(exam));
        grade.setExamID(exam.getExam_ID());
        grade.setStudentID(studentID);
        grade.setCourseID(exam.getCourseID());
        return grade;
    }

    private void addSolvedExam(Exam exam, String studentID) throws IOException {
        solvedExamDAO.addSolvedExam(createSolvedExam(exam, studentID));
    }

    private SolvedExam createSolvedExam(Exam exam, String studentID){
        SolvedExam solvedExam = new SolvedExam();
        solvedExam.setStudentID(studentID);
        solvedExam.setCourseID(exam.getCourseID());
        solvedExam.setExamID(exam.getExam_ID());
        solvedExam.setSolvedExamAnswers(retrieveStudentAnswers(exam));
        return solvedExam;
    }

    private List<String> retrieveStudentAnswers(Exam exam){
        List<String> solvedExamAnswers = new ArrayList<>();
        for(Question question : exam.getQuestions()){
            solvedExamAnswers.add(question.getStudentAnswer());
        }
        return solvedExamAnswers;
    }
}
