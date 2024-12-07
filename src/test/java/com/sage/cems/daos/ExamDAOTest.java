package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.TimeConversion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamDAOTest {

    @Test
    void getAllExams() {

    }

    @Test
    void testGetAllExams() throws Exception {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        List<Exam> exams;
        exams = examDAO.getAllExams();
        System.out.println(exams.getFirst());
    }

    @Test
    void addExam() throws IOException {
        // there is a Course called CS50 in the text file
        // no need to create one from scratch here.
        
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        Date date = new Date();
        List<Question> questions = List.of();
//        Exam exam = new Exam(10,"CS50",123,date,"MidTerm",30d,false,questions);
//        examDAO.addExam(exam);

    }

    @Test
    void updateExam() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());
        Date newDate = Date.from(LocalDateTime.of(2024, 12, 7, 23, 42, 30)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Exam exam = null;
        List<Exam> exams = examDAO.getAllExams("5");
        for (Exam e :exams) {
            if (e.getExam_ID().equals("5")) {
                exam = e;
                break;
            }
        }
        assert exam != null;
        exam.setExamStartDate(newDate);
        exam.setExamDuration(TimeConversion.secondsToMilliseconds(40 * 60 * 60));
        examDAO.updateExam(exam);
    }

    @Test
    void deleteExam() throws IOException {
        ExamDAO examDAO = new ExamDAO(new CEMSFileManager());

        Course course = new Course();
        course.setCourseName("computer science");
        course.setCourseID("CS60");
        Date date = convertStringToDate("Mon Dec 02 02:09:10 EET 2024");

        List<Question> questions = List.of();
//        Exam exam = new Exam(10,"CS50",123,date,"MidTerm",30.0,false,questions);
//        exam.setExam_ID("6");
//        examDAO.deleteExam(exam);
    }
    private Date convertStringToDate(String stringDate) {

        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        // Parse the date string into a Date object
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}