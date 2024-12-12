package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.services.GradeService;
import com.sage.cems.services.ReCorrectionService;
import com.sage.cems.views.ViewFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem requestReCorrection = new MenuItem("Request Re-correction");

    private Grade grade;
    private final BooleanProperty hasSubmittedRequestProperty = new SimpleBooleanProperty();
    private GradeService gradeService;
    private ReCorrectionService reCorrectionService;

    public void setGradeData(Grade grade, GradeService gradeService, ReCorrectionService reCorrectionService) {
        this.grade = grade;
        this.gradeService = gradeService;
        this.reCorrectionService = reCorrectionService;
        updateView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        updateReCorrectionMenuItem();
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
        ViewFactory.getInstance().showCorrectedExamWindow(solvedExam, grade.getStudentID(), hasSubmittedRequestProperty, ((Stage) examNameLabel.getScene().getWindow()), gradeService, reCorrectionService);
    }

    private void onRequestReCorrection() {
        ViewFactory.getInstance().showReCorrectionWindow(grade, reCorrectionService, hasSubmittedRequestProperty);
    }

    private void updateReCorrectionMenuItem() {
        try {
            boolean hasRequestBefore = reCorrectionService.hasRequestReCorrection(grade.getStudentID(), grade.getExamID());
            hasSubmittedRequestProperty.addListener((_, _, newVal) -> requestReCorrection.setDisable(newVal));
            hasSubmittedRequestProperty.set(hasRequestBefore);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

}
