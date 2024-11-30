package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamDAOTest {

    @Test
    void getAllExams() {

    }

    @Test
    void testGetAllExams() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        List<Exam> exams;
        exams = examDAO.getAllExams();
        System.out.println(exams.getFirst().getExamDuration());
    }

    @Test
    void addExam() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());

        Course course = new Course();
        course.setCourseName("computer science");
        course.setCourseID("CS60");
        Date date = new Date();
        List<Question> questions = List.of();
        Exam exam = new Exam(10,"1",course,date,date,"MidTerm",30d,false,questions);
        examDAO.addExam(exam);

    }

    @Test
    void updateExam() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());

        Course course = new Course();
        course.setCourseName("computer science");
        course.setCourseID("CS54");
        Date date = new Date();
        List<Question> questions = List.of();
        Exam exam = new Exam(10,"2",course,date,date,"Final",50d,false,questions);
        examDAO.updateExam(exam);

    }

    @Test
    void deleteExam() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());

        Course course = new Course();
        course.setCourseName("computer science");
        course.setCourseID("CS60");
        Date date = new Date();
        List<Question> questions = List.of();
        Exam exam = new Exam(10,"1",course,date,date,"MidTerm",30d,false,questions);
        examDAO.deleteExam(exam);
    }
}