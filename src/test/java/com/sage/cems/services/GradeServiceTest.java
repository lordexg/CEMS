package com.sage.cems.services;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.models.SolvedExam;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeServiceTest {

    @Test
    void getStudentGrades() throws IOException {
        GradeService gradeService = new GradeService();
        List<Grade> grades = gradeService.getStudentGrades("1");
        for (Grade grade: grades){
            System.out.println(grade.getMark());
        }
    }

    @Test
    void getStudentSolvedExam() throws IOException {
        GradeService gradeService = new GradeService();
        Exam solvedExam = gradeService.getStudentSolvedExam("4","1");

    }

    @Test
    void getGradeSymbol() throws IOException {
        GradeService gradeService = new GradeService();
        Grade grade = new Grade(25,"1","CS50","4","1");
        System.out.println(gradeService.getGradeSymbol(grade));
    }
}