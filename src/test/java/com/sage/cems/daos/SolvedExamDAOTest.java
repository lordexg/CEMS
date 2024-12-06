package com.sage.cems.daos;

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
        System.out.println(exams.size());
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