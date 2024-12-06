package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Student;
import com.sage.cems.models.user.Role;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class StudentDAO {
    private final FileManager fileManager;
    private final CourseDAO courseDAO;

    public StudentDAO(FileManager fileManager) {
        this.fileManager = fileManager;
        this.courseDAO = new CourseDAO(fileManager);
    }

    /**
     * @param keyWord student's <code>name</code>, <code>ID</code>, <code>phone</code> ...
     */
    public List<Student> getAllStudents(String keyWord) throws IOException {
        List<Map<ColumnName, String>> students = fileManager.getRows(TableName.STUDENT, keyWord);

        if (students.isEmpty()) {
            return new ArrayList<>();
        }

        return createStudentsList(students);
    }

    public List<Student> getAllStudents() throws IOException {
        List<Map<ColumnName, String>> students = fileManager.getAllRows(TableName.STUDENT);
        if(students.isEmpty()) {
            return new ArrayList<>();
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

    private void populateStudentFields(Student student, Map<ColumnName, String> studentMap, Map<ColumnName, String> accountMap) throws IOException {
        student.setID(studentMap.get(ColumnName.STUDENT_ID));
        student.setFirstName(studentMap.get(ColumnName.STUDENT_FIRST_NAME));
        student.setLastName(studentMap.get(ColumnName.STUDENT_LAST_NAME));
        student.setEmail(studentMap.get(ColumnName.STUDENT_EMAIL));
        student.setPhoneNumber(studentMap.get(ColumnName.STUDENT_PHONE_NUMBER));

        student.setPassword(accountMap.get(ColumnName.ACCOUNT_PASSWORD));
        student.setRole(Role.valueOf(accountMap.get(ColumnName.ACCOUNT_ROLE)));
        /*
            - search in enrollment table to get student's courses ID
            - using courses ID, search for the course in Course table and create courses list
                assign this list to student.courses.
            - enrollment list for a student: {{userID, courseID}, {1,2}, {1,3}, {1,15} ...}
         */
        List<Map<ColumnName, String>> enrollments = fileManager.getRows(TableName.ENROLLMENT, student.getID());

        List<Course> courses = new ArrayList<>();
        for(Map<ColumnName, String> enrollment : enrollments) {
            if (enrollment.get(ColumnName.STUDENT_ID).equals(student.getID())) {
                String courseID = enrollment.get(ColumnName.COURSE_ID);
                courses.add(courseDAO.getCourse(courseID));
            }
        }

        student.setCourses(courses);
    }

    private Student createStudent(Map<ColumnName, String> studentMap, Map<ColumnName, String> accountMap) throws IOException {
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


