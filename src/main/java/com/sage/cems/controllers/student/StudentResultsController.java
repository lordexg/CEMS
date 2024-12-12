package com.sage.cems.controllers.student;

import com.sage.cems.models.Grade;
import com.sage.cems.services.GradeService;
import com.sage.cems.services.ReCorrectionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentResultsController {
    // Exam - Course - Grade - Total Grade [Show corrected exam - Request re-correction]
    public Label noResultsMessage;
    public VBox gradesPane;
    public ScrollPane gradesScrollPane;

    private GradeService gradeService;
    private ReCorrectionService reCorrectionService;
    private String studentID;

    public StudentResultsController() {
        try {
            this.gradeService = new GradeService();
            this.reCorrectionService = new ReCorrectionService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
        loadGrades();
    }

    private void loadGrades() {
        gradesPane.getChildren().clear();
        noResultsMessage.setVisible(false);
        gradesScrollPane.setVisible(true);
        List<Grade> studentGrades;
        try {
            studentGrades = gradeService.getStudentGrades(studentID);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            return;
        }
        if (studentGrades.isEmpty()) {
            noResultsMessage.setVisible(true);
            gradesScrollPane.setVisible(false);
            return;
        }
        for (Grade grade : studentGrades) {
            gradesPane.getChildren().add(generateGradeView(grade));
        }
        gradesPane.getChildren().getLast().getStyleClass().add("last-row");
    }

    private AnchorPane generateGradeView(Grade grade) {
        AnchorPane gradeView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/grade.fxml"));
            gradeView = loader.load();
            ((GradeController) loader.getController()).setGradeData(grade, gradeService, reCorrectionService);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return gradeView;
    }
}
