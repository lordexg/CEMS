package com.sage.cems.daos;

import com.sage.cems.models.Question;
import com.sage.cems.models.SolvedExam;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolvedExamDAOTest {

    @Test
    void getAllSolvedExams() throws IOException {
        SolvedExamDAO solvedExamDAO = new SolvedExamDAO(new CEMSFileManager());
        List<SolvedExam> exams = solvedExamDAO.getAllSolvedExams();
        for(String answer : exams.getFirst().getSolvedExamAnswers()){
            System.out.println(answer);
        }
    }

    @Test
    void testGetAllSolvedExams() {
    }

    @Test
    void addSolvedExam() {
    }

    @Test
    void updateSolvedExam() {
    }

    @Test
    void deleteSolvedExam() {
    }
}