package com.sage.cems.services;

import com.sage.cems.daos.CourseDAO;
import com.sage.cems.models.Course;
import com.sage.cems.models.Student;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.SearchType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    @Test
    void getStudentByProperty() throws IOException {
        AdminService adminService = new AdminService();

        List<Student> students = adminService.getStudentByProperty("1", SearchType.ID);
        System.out.println(students.getFirst().getFirstName());

    }

    @Test
    void addStudent() {

    }

    @Test
    void editStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getUnEnrolledCourses() throws IOException {
        AdminService adminService = new AdminService();
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());
        Student student = new Student();
        student.setID("1");
        student.setCourses(courseDAO.getUserCourses(student));
        List<Course> unEnrolledCourses = adminService.getUnEnrolledCourses(student);
        for(Course course : unEnrolledCourses){
            System.out.println(course.getCourseID());
        }
    }
}