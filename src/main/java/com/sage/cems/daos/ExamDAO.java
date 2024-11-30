package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Student;
import com.sage.cems.models.user.Role;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExamDAO {

    private final FileManager fileManager;
    private final CourseDAO courseDAO;

    public ExamDAO(FileManager fileManager) {
        this.fileManager = fileManager;
        this.courseDAO = new CourseDAO(fileManager);
    }

    public List<Exam> getAllExams(String keyWord) throws IOException {
        List<Map<ColumnName, String>> exams = fileManager.getRows(TableName.EXAM, keyWord);

        if (exams.isEmpty()) {
            throw new IOException("No student found");
        }

        return createExamsList(exams);
    }

    public List<Exam> getAllExams() throws IOException {
        List<Map<ColumnName, String>> exams = fileManager.getAllRows(TableName.EXAM);
        if(exams.isEmpty()) {
            throw new IOException("No student found");
        }
        return createExamsList(exams);
    }

    public void addExam(Exam exam) throws IOException {
        fileManager.insertRow(TableName.EXAM, createExamMap(exam));
    }

    public void updateExam(Exam exam) throws IOException {
        fileManager.updateRow(TableName.EXAM, createExamMap(exam));
    }

    public void deleteExam(Exam exam) throws IOException {
        fileManager.deleteRow(TableName.EXAM, createExamMap(exam));
    }

    private void populateExamFields(Exam exam, Map<ColumnName, String> examMap) throws IOException {

        exam.setExam_ID(examMap.get(ColumnName.EXAM_ID));
        exam.setExamName(examMap.get(ColumnName.EXAM_NAME));
        // should include exam_duration in ms
        // handling converting start_date from string to date
        //exam.setExamDuration(Date.valueOf(examMap.get(ColumnName.EXAM_DURATION)));
        //exam.setExamStartDate(Date.valueOf(examMap.get(ColumnName.EXAM_START_DATE)));
        exam.setMark(Double.parseDouble(examMap.get(ColumnName.EXAM_FULL_MARK)));
        exam.setExamLength(Integer.parseInt(examMap.get(ColumnName.EXAM_LENGTH)));
        exam.setApproved(Boolean.parseBoolean(examMap.get(ColumnName.EXAM_IS_APPROVED)));
    }

    private Exam createExam(Map<ColumnName, String> examMap, String courseID) throws IOException {
        Exam exam = new Exam();
        populateExamFields(exam, examMap);
        exam.setCourse(courseDAO.getCourse(courseID));
        return exam;
    }

    private Map<ColumnName, String> createExamMap(Exam exam){
        Map<ColumnName, String> newExam = new TreeMap<>();
        newExam.put(ColumnName.EXAM_ID, exam.getExam_ID());
        newExam.put(ColumnName.EXAM_NAME, exam.getExamName());
        newExam.put(ColumnName.EXAM_LENGTH, String.valueOf(exam.getExamLength()));
        newExam.put(ColumnName.EXAM_FULL_MARK, String.valueOf(exam.getMark()));
        newExam.put(ColumnName.EXAM_DURATION, String.valueOf(exam.getExamDuration()));
        //newExam.put(ColumnName.EXAM_QUESTIONS, questionDAO.getQuestions());
        newExam.put(ColumnName.EXAM_START_DATE, String.valueOf(exam.getExamStartDate()));
        newExam.put(ColumnName.EXAM_IS_APPROVED, String.valueOf(exam.isApproved()));
        newExam.put(ColumnName.COURSE_ID, String.valueOf(exam.getCourse().getCourseID()));
        return newExam;
    }

    private List<Exam> createExamsList(List<Map<ColumnName, String>> exams) throws IOException {
        // create exams list
        List<Exam> examsList = new ArrayList<>();
        for (Map<ColumnName, String> exam : exams) {
            examsList.add(createExam(exam, exam.get(ColumnName.COURSE_ID)));
        }
        return examsList;
    }



}
