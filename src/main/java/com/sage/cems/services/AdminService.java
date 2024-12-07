package com.sage.cems.services;

import com.sage.cems.daos.CourseDAO;
import com.sage.cems.daos.StudentDAO;
import com.sage.cems.models.Course;
import com.sage.cems.models.Student;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.SearchType;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;

    public AdminService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.courseDAO = new CourseDAO(fileManager);
        this.studentDAO = new StudentDAO(fileManager);
    }


    public List<Student> getStudentByProperty(String keyWord, SearchType searchType) throws IOException {
        List<Student> students = studentDAO.getAllStudents(keyWord);
        return students.stream()
                .filter(student -> filterCondition(student, keyWord, searchType))
                .collect(Collectors.toList());
    }

    public void addStudent(Student student) throws IOException {
        studentDAO.addStudent(student);
    }

    public void editStudent(Student student) throws IOException {
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(Student student) throws IOException {
        studentDAO.deleteStudent(student);
    }

    public List<Course> getUnEnrolledCourses(Student student) throws IOException {
        List<Course> allCourses = courseDAO.getAllCourses();
        List<Course> unEnrolledCourses = new ArrayList<>();
        for (Course course : allCourses) {
            boolean exist = false;
            for (Course course1 : student.getCourses()){
                if(course1.getCourseID().equals(course.getCourseID())){
                    exist = true;
                    break;
                }
            }
            if(!exist){
                unEnrolledCourses.add(course);
            }
        }
        return unEnrolledCourses;
    }

    private boolean filterCondition(Student student, String keyWord, SearchType searchType) {
        return switch (searchType) {
            case FIRST_NAME -> student.getFirstName().contains(keyWord);
            case EMAIL -> student.getEmail().contains(keyWord);
            case ID -> student.getID().contains(keyWord);
            case PHONE_NUMBER -> student.getPhoneNumber().contains(keyWord);
            case LAST_NAME -> student.getLastName().contains(keyWord);
        };
    }
}
