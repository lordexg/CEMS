package com.sage.cems.controllers.student;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentCourseExamsController implements Initializable {
    public Label header;
    public Label noExamsMessage;
    public TilePane examsPane;
    public ChoiceBox<ExamsViewType> examsChoiceBox;

    private Course course = null;
    private String studentId;

    public void setCourseData(Course course, String studentId) {
        this.course = course;
        this.studentId = studentId;
        loadExams(examsChoiceBox.valueProperty().get());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<ExamsViewType> searchTypes = FXCollections.observableList(List.of(ExamsViewType.values()));
        examsChoiceBox.setItems(searchTypes);
        examsChoiceBox.setValue(ExamsViewType.ALL);
        examsChoiceBox.valueProperty().addListener((_, _, newVal) -> loadExams(newVal));
    }

    private void loadExams(ExamsViewType examsViewType) {
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
            boolean shouldAdd = switch (examsViewType) {
                case ALL -> true;
                case UPCOMING -> !exam.isCompleted();
                case COMPLETED -> exam.isCompleted();
            };

            if (shouldAdd) {
                examsPane.getChildren().add(generateExamView(exam));
            }
        }
    }

    private AnchorPane generateExamView(Exam exam) {
        AnchorPane examView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/exam.fxml"));
            examView = loader.load();
            ((ExamController)loader.getController()).setExamData(exam, studentId);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return examView;
    }
}
