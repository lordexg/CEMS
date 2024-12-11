package com.sage.cems.daos;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.models.user.User;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class CourseDAO {
    private final FileManager fileManager;
    private final ExamDAO examDAO;
    public CourseDAO(FileManager fileManager) {
        this.fileManager = fileManager;
        this.examDAO = new ExamDAO(fileManager);
    }

    public Course getCourse(String keyWord) throws IOException {
        List<Map<ColumnName, String>> courses = fileManager.getRows(TableName.COURSE, keyWord);
        if (courses.isEmpty()) {
            return new Course();
        }
        return createCourse(courses.getFirst());
    }

    /**
        Get all courses in the system
      */
    public List<Course> getAllCourses() throws IOException {
        List<Map<ColumnName, String>> courses = fileManager.getAllRows(TableName.COURSE);
        if(courses.isEmpty()) {
            return new ArrayList<>();
        }
        return createCoursesList(courses);
    }

    // will be used in initializing the Student/Lecturer
    public List<Course> getUserCourses(User user) throws IOException {
        List<Map<ColumnName,String>> enrollMap = fileManager.getRows(TableName.ENROLLMENT, user.getID());
        if (enrollMap.isEmpty()) {
            return new ArrayList<>();
        }

        // using enrollment table, I got courses IDs
        // Imma use them to get the courses from courses Table
        List<Map<ColumnName, String>> coursesMapList = new ArrayList<>();
        for(Map<ColumnName, String> enroll : enrollMap) {
            if (enroll.get(ColumnName.STUDENT_ID).equals(user.getID())) {
                String courseID = enroll.get(ColumnName.COURSE_ID);
                Map<ColumnName, String> course = fileManager.getRows(TableName.COURSE, courseID).getFirst();
                coursesMapList.add(course);
            }
        }
        return createCoursesList(coursesMapList);
    }

    public void addCourse(Course course) throws IOException {
        fileManager.insertRow(TableName.COURSE, createCourseMap(course));
    }

    /**
     @param course the course to be assigned
     @param user the user to whom the course is assigned
    */
    public void assignCourse(Course course, User user) throws IOException {
        Map<ColumnName, String> newEnrollment = new TreeMap<>();
        newEnrollment.put(ColumnName.ACCOUNT_ID, user.getID());
        newEnrollment.put(ColumnName.COURSE_ID, course.getCourseID());

        List<Map<ColumnName, String>> search = fileManager.getRows(TableName.ENROLLMENT, user.getID());
        for (Map<ColumnName, String> row : search) {
            if (row.get(ColumnName.COURSE_ID).equals(course.getCourseID())) {
                return;
            }
        }
        // update the file system
        fileManager.insertRow(TableName.ENROLLMENT, newEnrollment);
    }

    /**
     * <strong>This function deletes a course and all its enrollments</strong>
     */
    public void deleteCourse(Course course) throws IOException {
        fileManager.deleteRow(TableName.COURSE, createCourseMap(course));

        // Delete all enrollments for this course ID.
        List<Map<ColumnName, String>> enrolls = fileManager.getRows(TableName.ENROLLMENT, course.getCourseID());
        for(Map<ColumnName, String> enroll : enrolls) {
            fileManager.deleteRow(TableName.ENROLLMENT, enroll);
        }

        // Delete all exams for this course ID.
        List<Map<ColumnName, String>> exams = fileManager.getRows(TableName.EXAM, course.getCourseID());
        for(Map<ColumnName, String> exam : exams) {
            fileManager.deleteRow(TableName.EXAM, exam);
        }
    }

    public void deleteCourseFromUser(User user, Course course) throws IOException {
        Map<ColumnName, String> enrollment = new TreeMap<>();
        enrollment.put(ColumnName.STUDENT_ID, user.getID());
        enrollment.put(ColumnName.COURSE_ID, course.getCourseID());
        fileManager.deleteRow(TableName.ENROLLMENT, enrollment);
    }


    private void populateCourseFields(Map<ColumnName, String> courseMap, Course course) throws IOException {
        course.setCourseID(courseMap.get(ColumnName.COURSE_ID));
        course.setCourseName(courseMap.get(ColumnName.COURSE_NAME));
        List<Exam> exams;
        try {
            exams = examDAO.getAllExams(course.getCourseID());
        } catch (Exception e) {
            exams = new ArrayList<>();
        }
        course.setExams(exams);
    }

    private Course createCourse(Map<ColumnName, String> courseMap) throws IOException {
        Course newCourse = new Course();
        populateCourseFields(courseMap, newCourse);
        return newCourse;
    }

    // Used in some functions that interacts with the CEMS File System (insertion, deletion)
    private Map<ColumnName, String> createCourseMap(Course course){
        Map<ColumnName, String> newCourse = new TreeMap<>();
        newCourse.put(ColumnName.COURSE_ID, course.getCourseID());
        newCourse.put(ColumnName.COURSE_NAME, course.getCourseName());
        return newCourse;
    }

    private List<Course> createCoursesList(List<Map<ColumnName, String>> courses) throws IOException {
        List<Course> courseList = new ArrayList<>();
        for(Map<ColumnName, String> course : courses) {
            courseList.add(createCourse(course));
        }
        return courseList;
    }
}