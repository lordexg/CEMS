package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.services.GradeService;
import javafx.application.Platform;
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
    private final Stage parentStage;
    private GradeService gradeService;

    public CorrectedExamController(Exam exam, String studentID, Stage parentStage) {
        this.exam = exam;
        this.parentStage = parentStage;
        this.studentID = studentID;
        try {
            this.gradeService = new GradeService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
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
}
