package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Student;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class StudentDAOTest {

    @Test
    void getAllStudents() throws IOException {
        StudentDAO studentDAO = new StudentDAO(new CEMSFileManager());
        List<Student> students = studentDAO.getAllStudents("null");
        for (Student student : students) {
            System.out.println(student.getID() + " " +
                    student.getFirstName() + " " +
                    student.getLastName() + " " +
                    student.getEmail());
        }
    }
    @Test
    void testUpdateStudent() throws IOException {
        StudentDAO studentDAO = new StudentDAO(new CEMSFileManager());
        List<Student> students = studentDAO.getAllStudents("null");
        students.get(0).setFirstName("John");
        studentDAO.updateStudent(students.get(0));
    }
    @Test
    void testGetAllStudents() {

    }

    @Test
    void addStudent() throws IOException {
    }

    @Test
    void deleteStudent() throws IOException {

    }
    @Test
    void newTest() throws IOException {
        StudentDAO studentDAO = new StudentDAO(new CEMSFileManager());
        Student student = studentDAO.getAllStudents("1").getFirst();

        for (Exam exam : student.getCourses().getFirst().getExams()) {
            System.out.println(exam.getExamName());
        }
    }
}