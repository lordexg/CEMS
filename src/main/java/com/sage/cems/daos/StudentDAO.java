package com.sage.cems.daos;

import com.sage.cems.models.Student;
import com.sage.cems.models.user.Role;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentDAO {
    private final FileManager fileManager;

    public StudentDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    // this function takes a student property and return all matching.
    // student properties like: name, id, phone ...
    public List<Student> getAllStudents(String keyWord) throws IOException {
        List<Map<ColumnName, String>> students = fileManager.getRows(TableName.STUDENT, keyWord);

        if (students.isEmpty()) {
            throw new IOException("No student found");
        }

        return createStudentsList(students);
    }

    public List<Student> getAllStudents() throws IOException {
        List<Map<ColumnName, String>> students = fileManager.getAllRows(TableName.STUDENT);
        if(students.isEmpty()) {
            throw new IOException("No student found");
        }
        return createStudentsList(students);
    }

    public void addStudent(Student student) throws IOException {
        fileManager.insertRow(TableName.STUDENT, createStudentMap(student));
    }

    public void updateStudent(Student student) throws IOException {
        fileManager.updateRow(TableName.STUDENT, createStudentMap(student));
    }

    public void deleteStudent(Student user) throws IOException {
        fileManager.deleteRow(TableName.STUDENT, createStudentMap(user));
    }

    private static void populateStudentFields(Student student, Map<ColumnName, String> studentMap, Map<ColumnName, String> accountMap) {
        student.setID(studentMap.get(ColumnName.STUDENT_ID));
        student.setFirstName(studentMap.get(ColumnName.STUDENT_FIRST_NAME));
        student.setLastName(studentMap.get(ColumnName.STUDENT_LAST_NAME));
        student.setEmail(studentMap.get(ColumnName.STUDENT_EMAIL));
        student.setPhoneNumber(studentMap.get(ColumnName.STUDENT_PHONE_NUMBER));

        student.setPassword(accountMap.get(ColumnName.ACCOUNT_PASSWORD));
        student.setRole(Role.valueOf(accountMap.get(ColumnName.ACCOUNT_ROLE)));
    }

    private Student createStudent(Map<ColumnName, String> studentMap, Map<ColumnName, String> accountMap) {
        Student student = new Student();
        populateStudentFields(student, studentMap, accountMap);
        return student;
    }

    private Map<ColumnName, String> createStudentMap(Student student){
        Map<ColumnName, String> newStudent = new TreeMap<>();
        newStudent.put(ColumnName.STUDENT_ID, student.getID());
        newStudent.put(ColumnName.STUDENT_FIRST_NAME, student.getFirstName());
        newStudent.put(ColumnName.STUDENT_LAST_NAME, student.getLastName());
        newStudent.put(ColumnName.STUDENT_PHONE_NUMBER, student.getPhoneNumber());
        newStudent.put(ColumnName.STUDENT_EMAIL, student.getEmail());
        return newStudent;
    }

    private List<Student> createStudentsList(List<Map<ColumnName, String>> students) throws IOException {
        /*
            From accounts table, user ID is the common with students table.
            so I have to search for each student account using his/her ID
            to assign the correct password to each student
        */

        List<Map<ColumnName, String>> accounts = new ArrayList<>();
        for (Map<ColumnName, String> student : students) {
            String userID = student.get(ColumnName.STUDENT_ID);
            Map<ColumnName, String> account = fileManager.getRows(TableName.ACCOUNT, userID).getFirst();
            accounts.add(account);
        }

        // create students list
        List<Student> StudentsList = new ArrayList<>();
        for(int i = 0; i < students.size(); i++) {
            StudentsList.add(createStudent(students.get(i), accounts.get(i)));
        }
        return StudentsList;
    }
}


