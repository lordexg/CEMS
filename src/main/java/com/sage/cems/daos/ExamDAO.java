package com.sage.cems.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.util.ColumnName;
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

    public List<Exam> getAllExams(String keyWord) throws IOException {
        List<Map<ColumnName, String>> exams = fileManager.getRows(TableName.EXAM, keyWord);

        if (exams.isEmpty()) {
            throw new IOException("No exams found");
        }

        return createExamsList(exams);
    }

    public List<Exam> getAllExams() throws IOException {
        List<Map<ColumnName, String>> exams = fileManager.getAllRows(TableName.EXAM);
        if(exams.isEmpty()) {
            throw new IOException("No exams found");
        }
        return createExamsList(exams);
    }

    public void addExam(Exam exam) throws IOException {
        /*
            Generate exam ID based on the EXAM_TABLE last EXAM_ID,
            since its ID is unique throughout the system not in its course only.
        */

        // might be null (no rows found)
        String lastID = fileManager.getLastRow(TableName.EXAM).get(ColumnName.EXAM_ID);

        String newID;
        try{
            newID = String.valueOf(Integer.parseInt(lastID) + 1);
        }catch(NumberFormatException e){
            newID = "1";
            System.out.println("That's the first Exam");
        }

        exam.setExam_ID(newID);
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
        exam.setExamDuration(Long.parseLong(examMap.get(ColumnName.EXAM_DURATION)));
        exam.setExamStartDate(convertStringToDate(examMap.get(ColumnName.EXAM_START_DATE)));
        exam.setFullMark(Double.parseDouble(examMap.get(ColumnName.EXAM_FULL_MARK)));
        exam.setExamLength(Integer.parseInt(examMap.get(ColumnName.EXAM_LENGTH)));
        exam.setApproved(Boolean.parseBoolean(examMap.get(ColumnName.EXAM_IS_APPROVED)));
        exam.setCompleted(Boolean.parseBoolean(examMap.get(ColumnName.EXAM_IS_COMPLETED)));
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
    private Map<ColumnName, String> createExamMap(Exam exam) throws IOException {
        Map<ColumnName, String> newExam = new TreeMap<>();
        newExam.put(ColumnName.EXAM_ID, exam.getExam_ID());
        newExam.put(ColumnName.EXAM_NAME, exam.getExamName());
        newExam.put(ColumnName.EXAM_LENGTH, String.valueOf(exam.getExamLength()));
        newExam.put(ColumnName.EXAM_FULL_MARK, String.valueOf(exam.getFullMark()));
        newExam.put(ColumnName.EXAM_DURATION, String.valueOf(exam.getExamDuration()));
        //newExam.put(ColumnName.EXAM_QUESTIONS, questionDAO.getQuestions());
        newExam.put(ColumnName.EXAM_START_DATE, String.valueOf(exam.getExamStartDate()));
        newExam.put(ColumnName.EXAM_IS_APPROVED, String.valueOf(exam.isApproved()));
        newExam.put(ColumnName.EXAM_IS_COMPLETED, String.valueOf(exam.isCompleted()));
        newExam.put(ColumnName.COURSE_ID, String.valueOf(exam.getCourseID()));
        return newExam;
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