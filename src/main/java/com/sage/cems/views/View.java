package com.sage.cems.views;

public enum View {
    STUDENT_HOME("student/student-home.fxml"),
    STUDENT_COURSES("student/student-courses.fxml"),
    STUDENT_RESULTS("student/student-results.fxml"),
    STUDENT_PROFILE("student/student-profile.fxml"),
    STUDENT_COURSE_EXAMS("student/student-course-exams.fxml");

    private final String filePath;

    View(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
