package com.sage.cems.daos;

import com.sage.cems.models.SolvedExam;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class SolvedExamDAO {

    private final FileManager fileManager;

    public SolvedExamDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public List<SolvedExam> getAllSolvedExams(String keyWord) throws IOException {
        List<Map<ColumnName, String>> solvedSolvedExams = fileManager.getRows(TableName.SOLVED_EXAM, keyWord);

        if (solvedSolvedExams.isEmpty()) {
            throw new IOException("No solvedSolvedExams found");
        }

        return createSolvedExamsList(solvedSolvedExams);
    }

    public List<SolvedExam> getAllSolvedExams() throws IOException {
        List<Map<ColumnName, String>> solvedSolvedExams = fileManager.getAllRows(TableName.SOLVED_EXAM);
        if(solvedSolvedExams.isEmpty()) {
            throw new IOException("No solvedSolvedExams found");
        }
        return createSolvedExamsList(solvedSolvedExams);
    }

    public void addSolvedExam(SolvedExam solvedSolvedExam) throws IOException {
        fileManager.insertRow(TableName.SOLVED_EXAM, createSolvedExamMap(solvedSolvedExam));
    }

    public void updateSolvedExam(SolvedExam solvedSolvedExam) throws IOException {
        fileManager.updateRow(TableName.SOLVED_EXAM, createSolvedExamMap(solvedSolvedExam));
    }

    public void deleteSolvedExam(SolvedExam solvedSolvedExam) throws IOException {
        fileManager.deleteRow(TableName.SOLVED_EXAM, createSolvedExamMap(solvedSolvedExam));
    }

    private void populateSolvedExamFields(SolvedExam solvedSolvedExam, Map<ColumnName, String> solvedSolvedExamMap) throws IOException {
        solvedSolvedExam.setSolvedExamAnswers(List.of(solvedSolvedExamMap.get(ColumnName.QUESTION_STUDENT_ANSWER).split("|")));
        solvedSolvedExam.setCourseID(solvedSolvedExamMap.get(ColumnName.COURSE_ID));
        solvedSolvedExam.setExamID(solvedSolvedExamMap.get(ColumnName.EXAM_ID));
        solvedSolvedExam.setStudentID(solvedSolvedExam.getStudentID());
    }

    private SolvedExam createSolvedExam(Map<ColumnName, String> solvedSolvedExamMap) throws IOException {
        SolvedExam solvedSolvedExam = new SolvedExam();
        populateSolvedExamFields(solvedSolvedExam, solvedSolvedExamMap);
        return solvedSolvedExam;
    }

    // Used in some functions that interacts with the CEMS File System (insertion, deletion, update)
    private Map<ColumnName, String> createSolvedExamMap(SolvedExam solvedExam) throws IOException {
        Map<ColumnName, String> newSolvedExam = new TreeMap<>();
        newSolvedExam.put(ColumnName.EXAM_ID, solvedExam.getExamID());
        newSolvedExam.put(ColumnName.COURSE_ID, solvedExam.getCourseID());
        newSolvedExam.put(ColumnName.STUDENT_ID, solvedExam.getStudentID());
        newSolvedExam.put(ColumnName.SOLVED_EXAM_ANSWERS,String.join("|", solvedExam.getSolvedExamAnswers()));
        return newSolvedExam;
    }

    // create solvedSolvedExams list
    private List<SolvedExam> createSolvedExamsList(List<Map<ColumnName, String>> solvedSolvedExams) throws IOException {
        List<SolvedExam> solvedSolvedExamsList = new ArrayList<>();
        for (Map<ColumnName, String> solvedSolvedExam : solvedSolvedExams) {
            solvedSolvedExamsList.add(createSolvedExam(solvedSolvedExam));
        }
        return solvedSolvedExamsList;
    }
}