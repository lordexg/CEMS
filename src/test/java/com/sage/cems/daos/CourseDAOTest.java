package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Student;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    @Test
    void getCourse() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());
        Course course = courseDAO.getCourse("CS50");
        System.out.println(course.getCourseName());
    }

    @Test
    void getAllCourses() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());
        List<Course> course = courseDAO.getAllCourses();
        for (Course c : course) {
            System.out.println(c.getCourseID());
        }
    }

    @Test
    void getUserCourses() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());
        Student student = new Student();
        student.setID("1");
        List<Course> course = courseDAO.getUserCourses(student);
        for (Course c : course) {
            System.out.println(c.getCourseName() + " " + c.getCourseID());
        }
    }

    @Test
    void addCourse() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());

        Course course = new Course();
        course.setCourseName("computer science");
        course.setCourseID("CS50");
        courseDAO.addCourse(course);
    }

    @Test
    void assignCourse() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());

        Student user = new Student();
        user.setID("10");
        Course course = new Course();
        course.setCourseID("CS50");
        courseDAO.assignCourse(course, user);

        List<Course> courses = courseDAO.getUserCourses(user);
        System.out.println(courses.size());
        for (Course c : courses) {
            System.out.println(c.getCourseID());
        }
    }

    @Test
    void deleteCourse() throws IOException {
        CourseDAO courseDAO = new CourseDAO(new CEMSFileManager());
        Course course = new Course();
        course.setCourseID("CS50");
        course.setCourseName("wawa");
        courseDAO.deleteCourse(course);
    }
}