package com.sage.cems.services;

import com.sage.cems.daos.ExamDAO;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamServiceTest {

    @Test
    void submitExam() throws IOException {
        ExamService examService = new ExamService();

        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        Exam exam = getexam("5");
        for (Question question : exam.getQuestions()){
            question.setStudentAnswer(question.getCorrectAnswer());
        }
        exam.getQuestions().getFirst().setStudentAnswer("scla");
        examService.submitExam(exam,"1");
    }

    private Exam getexam(String examID) throws IOException {
        List<Exam> exams = new ArrayList<>();
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        exams = examDAO.getAllExams(examID);
        for(Exam exam : exams){
            if(exam.getExam_ID().equals(examID)){
                return exam;
            }
        }
        return new Exam();
    }
}