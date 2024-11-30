package com.sage.cems.services;

import com.sage.cems.daos.StudentDAO;
import com.sage.cems.models.Student;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;

import java.io.IOException;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() throws IOException{
        this.studentDAO = new StudentDAO(new CEMSFileManager());
    }

    public Student getStudentData(User user) throws IOException {
        List<Student> result = studentDAO.getAllStudents(user.getID());
        for (Student student : result) {
            if (student.getID().equals(user.getID()))
                return student;
        }
        return new Student();
    }
}
