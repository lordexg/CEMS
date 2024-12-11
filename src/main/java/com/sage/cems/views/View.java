package com.sage.cems.views;

public enum View {
    PROFILE("profile.fxml"),
    STUDENT_HOME("student/student-home.fxml"),
    STUDENT_COURSES("student/student-courses.fxml"),
    STUDENT_RESULTS("student/student-results.fxml"),
    STUDENT_COURSE_EXAMS("student/student-course-exams.fxml"),
    STUDENT_EXAM("student/student-exam.fxml"),

    ADMIN_HOME("admin/admin-home.fxml"),
    ADMIN_STUDENTS("admin/admin-students-section.fxml"),
    ADMIN_LECTURER("admin/admin-lecturer.fxml"),
    ADMIN_COURSES("admin/admin-courses.fxml"),
    ADMIN_GRADES("admin/admin-grades.fxml");

    private final String filePath;

    View(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
