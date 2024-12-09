package com.sage.cems.daos;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GradeDAO {
    private final FileManager fileManager;
    private final ExamDAO examDAO;

    public GradeDAO(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        this.examDAO = new ExamDAO(fileManager);
    }

    public void addGrade(Grade grade) throws IOException {
        // calculating the ID based on the last row in ACCOUNT TABLE
        String lastIDString = fileManager.getLastRow(TableName.GRADE).get(ColumnName.GRADE_ID);
        int newIDInt;
        // to handle empty Table --- lastIDString = "String without a Number in it"
        try{
            newIDInt = Integer.parseInt(lastIDString) + 1;
        }catch(NumberFormatException e){
            newIDInt = 1;
        }
        String newIDString = Integer.toString(newIDInt);
        grade.setGradeID(newIDString);

        fileManager.insertRow(TableName.GRADE, createGradeMap(grade));
    }

    public void updateGrade(Grade Grade) throws IOException {
        fileManager.updateRow(TableName.GRADE, createGradeMap(Grade));
    }

    public void deleteGrade(Grade Grade) throws IOException {
        fileManager.deleteRow(TableName.GRADE, createGradeMap(Grade));
    }

    public Grade getGrade(String keyWord) throws IOException {
        List<Map<ColumnName, String>> grades = fileManager.getRows(TableName.GRADE, keyWord);
        if (grades.isEmpty()) {
            return new Grade();
        }
        return createGrade(grades.getFirst());
    }

    public List<Grade> getAllGrades(String keyWord) throws IOException {
        List<Map<ColumnName, String>> grades = fileManager.getRows(TableName.GRADE, keyWord);

        if (grades.isEmpty()) {
            return new ArrayList<>();
        }

        return createGradesList(grades);
    }

    public List<Grade> getAllGrades() throws IOException {
        List<Map<ColumnName, String>> grades = fileManager.getAllRows(TableName.GRADE);
        if(grades.isEmpty()) {
            return new ArrayList<>();
        }
        return createGradesList(grades);
    }

    private List<Grade> createGradesList(List<Map<ColumnName, String>> grades) throws IOException {
        List<Grade> gradesList = new ArrayList<>();
        for (Map<ColumnName, String> grade : grades) {
            gradesList.add(createGrade(grade));
        }
        return gradesList;
    }


    private Map<ColumnName, String> createGradeMap(Grade grade) throws IOException {
        Map<ColumnName, String> newGrade = new TreeMap<>();
        newGrade.put(ColumnName.STUDENT_ID, grade.getStudentID());
        newGrade.put(ColumnName.COURSE_ID, grade.getCourseID());
        newGrade.put(ColumnName.EXAM_ID, grade.getExamID());
        newGrade.put(ColumnName.GRADE_MARK,String.valueOf(grade.getMark()));
        newGrade.put(ColumnName.GRADE_ID,String.valueOf(grade.getGradeID()));
        return newGrade;
    }

    private Grade createGrade(Map<ColumnName, String> gradeMap) throws IOException {
        Grade grade = new Grade();
        populateGradeFields(grade, gradeMap);
        return grade;
    }

    private void populateGradeFields(Grade grade, Map<ColumnName, String> gradeMap) throws IOException {
        grade.setExamID(gradeMap.get(ColumnName.EXAM_ID));
        grade.setGradeID(gradeMap.get(ColumnName.GRADE_ID));
        grade.setCourseID(gradeMap.get(ColumnName.COURSE_ID));
        grade.setMark(Integer.parseInt(gradeMap.get(ColumnName.GRADE_MARK)));
        grade.setStudentID(gradeMap.get(ColumnName.STUDENT_ID));
        Exam exam = null;
        for (Exam searchedExam : examDAO.getAllExams(grade.getExamID())) {
            if (searchedExam.getExam_ID().equals(grade.getExamID()))
                exam = searchedExam;
        }
        if (exam != null) {
            grade.setExamName(exam.getExamName());
            grade.setFullMark((int)exam.getFullMark());
        }
    }
}
