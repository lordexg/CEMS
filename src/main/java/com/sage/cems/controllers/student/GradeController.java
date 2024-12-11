package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.services.GradeService;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradeController implements Initializable {

    public Label examNameLabel;
    public Label courseIdLabel;
    public Label gradeLabel;
    public Label totalGradeLabel;
    public Button checkAnswersButton;
    public Button moreButton;

    private Grade grade;
    private GradeService gradeService;

    public void setGradeData(Grade grade, GradeService gradeService) {
        this.grade = grade;
        this.gradeService = gradeService;
        updateView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem requestReCorrection = new MenuItem("Request Re-correction");
        requestReCorrection.setOnAction(_ -> onRequestReCorrection());
        contextMenu.getItems().add(requestReCorrection);

        moreButton.setOnAction( _ -> {
            if (!contextMenu.isShowing()) {
                contextMenu.show(moreButton, Side.BOTTOM, 0, 0);
            } else {
                contextMenu.hide();
            }
        });
    }

    private void updateView() {
        examNameLabel.setText(grade.getExamName());
        courseIdLabel.setText(grade.getCourseID());
        gradeLabel.setText(grade.getMark() + " (" + gradeService.getGradeSymbol(grade) + ")");
        totalGradeLabel.setText(grade.getFullMark() + "");
        checkAnswersButton.setOnAction( _ -> onCheckAnswers());
    }

    private void onCheckAnswers() {
        Exam solvedExam = null;
        try {
            solvedExam = gradeService.getStudentSolvedExam(grade.getExamID(), grade.getStudentID());
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        examNameLabel.getScene().getWindow().hide();
        ViewFactory.getInstance().showCorrectedExamWindow(solvedExam, grade.getStudentID(), ((Stage) examNameLabel.getScene().getWindow()));
    }

    private void onRequestReCorrection() {

    }

}
