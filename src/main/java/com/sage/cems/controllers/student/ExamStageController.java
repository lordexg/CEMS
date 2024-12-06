package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.services.ExamService;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExamStageController implements Initializable {
    public Label examName;
    public Button submitButton;

    private final Exam exam;
    private final String studentId;
    private final Stage studentParentStage;
    private ExamService examService;

    public ExamStageController(Exam exam, String studentId, Stage studentParentWindow) {
        this.exam = exam;
        this.studentId = studentId;
        this.studentParentStage = studentParentWindow;
        try {
            this.examService = new ExamService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examName.setText(exam.getExamName() + ", ID: " + studentId);
        submitButton.setOnAction( _ -> onSubmit());
        Platform.runLater(() -> examName.getScene().getWindow().setOnHidden( _ -> studentParentStage.show()));
    }

    public void onSubmit() {
        // Logic
        try {
            examService.submitExam(exam, studentId);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        ((Stage) examName.getScene().getWindow()).close();
    }
}
