package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Grade;
import com.sage.cems.models.Question;
import com.sage.cems.services.GradeService;
import com.sage.cems.services.ReCorrectionService;
import com.sage.cems.views.ViewFactory;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorrectedExamController implements Initializable {

    public Label examNameLabel;
    public Label gradeLabel;
    public VBox questionsPane;
    public Button closeButton;
    public Button reCorrectionButton;

    private final Exam exam;
    private final String studentID;
    private final BooleanProperty hasSubmittedRequestProperty;
    private final Stage parentStage;
    private final GradeService gradeService;
    private final ReCorrectionService reCorrectionService;

    public CorrectedExamController(Exam exam, String studentID, BooleanProperty hasSubmittedRequestProperty, Stage parentStage, GradeService gradeService ,ReCorrectionService reCorrectionService) {
        this.exam = exam;
        this.studentID = studentID;
        this.hasSubmittedRequestProperty = hasSubmittedRequestProperty;
        this.parentStage = parentStage;
        this.gradeService = gradeService;
        this.reCorrectionService = reCorrectionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String formatedGrade = "";
        try {
            formatedGrade = gradeService.getFormatedGrade(exam.getExam_ID(), studentID);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        examNameLabel.setText(exam.getExamName());
        gradeLabel.setText(formatedGrade);
        closeButton.setOnAction( _ -> ((Stage) examNameLabel.getScene().getWindow()).close());
        Platform.runLater(() -> {
            loadQuestion();
            examNameLabel.getScene().getWindow().setOnHidden( _ -> parentStage.show());
        });
        updateReCorrectionButton();
        reCorrectionButton.setOnAction( _ -> onRequestReCorrection());

    }

    private void loadQuestion() {
        questionsPane.getChildren().clear();
        List<Question> questions = exam.getQuestions();
        for (int i = 0; i < questions.size(); ++i) {
            questionsPane.getChildren().add(generateQuestionView(questions.get(i), i+1));
        }
    }

    private AnchorPane generateQuestionView(Question question, int questionNumber) {
        AnchorPane questionView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/question.fxml"));
            questionView = loader.load();
            ((QuestionController) loader.getController()).setQuestionData(question, questionNumber, true);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return questionView;
    }

    private void updateReCorrectionButton() {
        try {
            boolean hasRequestBefore = reCorrectionService.hasRequestReCorrection(studentID, exam.getExam_ID());
            if (hasRequestBefore)
                reCorrectionButton.setDisable(true);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void onRequestReCorrection() {
        Grade tempGrade = new Grade();
        tempGrade.setExamName(exam.getExamName());
        tempGrade.setStudentID(studentID);
        tempGrade.setExamID(exam.getExam_ID());
        tempGrade.setCourseID(exam.getCourseID());
        ViewFactory.getInstance().showReCorrectionWindow(tempGrade, reCorrectionService, hasSubmittedRequestProperty);
        hasSubmittedRequestProperty.addListener((_, _, newVal) -> reCorrectionButton.setDisable(newVal));
    }

}
