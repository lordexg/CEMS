package com.sage.cems.controllers.student;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentCourseExamsController {
    public Label header;
    public Label noExamsMessage;
    public TilePane examsPane;

    private Course course = null;

    public void setCourse(Course course) {
        this.course = course;
        loadExams();
    }

    private void loadExams() {
        if (course == null)
            return;
        header.setText(course.getCourseName() + " " + "Exams");
        if (course.getExams() == null)
            return;
        examsPane.getChildren().clear();
        noExamsMessage.setVisible(false);
        List<Exam> exams = course.getExams();
        if (exams.isEmpty()) {
            noExamsMessage.setVisible(true);
            return;
        }
        for (Exam exam : exams) {
            examsPane.getChildren().add(generateExamView(exam));
        }
    }

    private AnchorPane generateExamView(Exam exam) {
        AnchorPane examView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/exam.fxml"));
            examView = loader.load();
            ((ExamController)loader.getController()).setExam(exam);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return examView;
    }

}
