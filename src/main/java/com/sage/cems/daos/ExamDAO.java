package com.sage.cems.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.EnumHelper;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class ExamDAO {

    private final FileManager fileManager;
    private final QuestionDAO questionDAO;

    public ExamDAO(FileManager fileManager) {
        this.fileManager = fileManager;
        this.questionDAO = new QuestionDAO(fileManager);
    }

    /**
     * @param courseID to retrieve the exams for the specified coursID
    * */
    public List<Exam> getAllExams(String courseID) throws IOException {
        List<Map<ColumnName, String>> allMatchedExams = fileManager.getRows(TableName.EXAM, courseID);

        if (allMatchedExams.isEmpty()) {
            return new ArrayList<>();
        }

        return createExamsList(allMatchedExams);
    }

    public List<Exam> getAllExams() throws IOException {
        List<Map<ColumnName, String>> exams = fileManager.getAllRows(TableName.EXAM);
        if(exams.isEmpty()) {
            return new ArrayList<>();
        }
        return createExamsList(exams);
    }

    public void addExam(Exam exam) throws IOException {
        fileManager.insertRow(TableName.EXAM, createExamMapADD(exam));
    }

    public void updateExam(Exam exam) throws IOException {
        fileManager.updateRow(TableName.EXAM, createExamMapDU(exam));
    }

    public void deleteExam(Exam exam) throws IOException {
        fileManager.deleteRow(TableName.EXAM, createExamMapDU(exam));
    }

    private void populateExamFields(Exam exam, Map<ColumnName, String> examMap) throws IOException {
        exam.setExam_ID(examMap.get(ColumnName.EXAM_ID));
        exam.setExamName(examMap.get(ColumnName.EXAM_NAME));
        exam.setExamDuration(Long.parseLong(examMap.get(ColumnName.EXAM_DURATION)));
        exam.setExamStartDate(convertStringToDate(examMap.get(ColumnName.EXAM_START_DATE)));
        exam.setFullMark(Double.parseDouble(examMap.get(ColumnName.EXAM_FULL_MARK)));
        exam.setExamLength(Integer.parseInt(examMap.get(ColumnName.EXAM_LENGTH)));
        exam.setApproved(Boolean.parseBoolean(examMap.get(ColumnName.EXAM_IS_APPROVED)));
        exam.setCourseID(examMap.get(ColumnName.COURSE_ID));

        List<Question> questions;
        try {
            questions = questionDAO.getQuestions(exam.getExam_ID());
        } catch (Exception e) {
            questions = new ArrayList<>();
        }
        exam.setQuestions(questions);
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

    private Exam createExam(Map<ColumnName, String> examMap) throws IOException {
        Exam exam = new Exam();
        populateExamFields(exam, examMap);
        return exam;
    }

    // Used in some functions that interacts with the CEMS File System (insertion, deletion, update)
    // used to add exam
    private Map<ColumnName, String> createExamMapADD(Exam exam) throws IOException {
        Map<ColumnName, String> newExam = new TreeMap<>();
        populateExamMap(newExam, exam);
        newExam.put(ColumnName.EXAM_ID, EnumHelper.getNewPK(TableName.EXAM));
        return newExam;
    }
    // used to delete/update an exam
    private Map<ColumnName, String> createExamMapDU(Exam exam) throws IOException {
        Map<ColumnName, String> newExam = new TreeMap<>();
        populateExamMap(newExam, exam);
        newExam.put(ColumnName.EXAM_ID, exam.getExam_ID());
        return newExam;
    }

    /**
     *  populates an ExamMap without adding an examID, cus different createExamMap functions will implement it
    */
    private void populateExamMap(Map<ColumnName, String> newExam, Exam exam) throws IOException {
        newExam.put(ColumnName.EXAM_NAME, exam.getExamName());
        newExam.put(ColumnName.EXAM_LENGTH, String.valueOf(exam.getExamLength()));
        newExam.put(ColumnName.EXAM_FULL_MARK, String.valueOf(exam.getFullMark()));
        newExam.put(ColumnName.EXAM_DURATION, String.valueOf(exam.getExamDuration()));
        newExam.put(ColumnName.EXAM_START_DATE, String.valueOf(exam.getExamStartDate()));
        newExam.put(ColumnName.EXAM_IS_APPROVED, String.valueOf(exam.isApproved()));
        newExam.put(ColumnName.COURSE_ID, String.valueOf(exam.getCourseID()));
    }

    // create exams list
    private List<Exam> createExamsList(List<Map<ColumnName, String>> exams) throws IOException {
        List<Exam> examsList = new ArrayList<>();
        for (Map<ColumnName, String> exam : exams) {
            examsList.add(createExam(exam));
        }
        return examsList;
    }
}