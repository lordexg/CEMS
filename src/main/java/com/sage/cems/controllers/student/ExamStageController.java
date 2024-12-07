package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.models.Question;
import com.sage.cems.services.ExamService;
import com.sage.cems.util.TimeConversion;
import com.sage.cems.views.PopUpType;
import com.sage.cems.views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sage.cems.util.TimeConversion.calculateRemainingTimeInSeconds;
import static com.sage.cems.util.TimeConversion.formatTime;

public class ExamStageController implements Initializable {
    public Label examName;
    public Label countdownTimer;
    public VBox questionsPane;
    public Button submitButton;

    private final Exam exam;
    private final String studentId;
    private final Stage studentParentStage;
    private ExamService examService;
    private final List<QuestionController> questionControllers = new ArrayList<>();

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
        examName.setText(exam.getExamName());
        submitButton.setOnAction( _ -> onSubmit());
        Platform.runLater(() -> {
            startCountDownTimer();
            loadQuestion();
            examName.getScene().getWindow().setOnHidden( _ -> studentParentStage.show());
            examName.getScene().getWindow().setOnCloseRequest(this::handleCloseRequest);
        });
    }

    private void startCountDownTimer() {
        updateCountdownLabel();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1), // Update every second
                    _ -> {
                        if (updateCountdownLabel()) {
                            timeline.stop();
                            endExam();
                        }
                    }
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timeline.play(); // Start the countdown

    }

    private boolean updateCountdownLabel() {
        // Calculate remaining time
        long examDurationInSeconds = TimeConversion.millisecondsToSeconds(exam.getExamDuration());
        long remainingTimeInSeconds = calculateRemainingTimeInSeconds(exam.getExamStartDate()) + examDurationInSeconds;
        if (remainingTimeInSeconds >= 0) {
            countdownTimer.setText(formatTime(remainingTimeInSeconds));
            return false;
        }
        return true;
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
            ((QuestionController) loader.getController()).setQuestionData(question, questionNumber);
            questionControllers.add(loader.getController());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return questionView;
    }

    // Exam Time ends - Student clicks x - Student clicks submit
    private void onSubmit() {
        List<Question> answeredQuestions;
        try {
            answeredQuestions = getSolvedQuestions(true);
        } catch (Exception e) {
            return;
        }
        boolean hasConfirmed = ViewFactory.getInstance().showPopUp("Submit confirmation",
                "Are you sure that you will submit the exam before the timer ends?",
                PopUpType.CONFIRMATION);
        if (!hasConfirmed)
            return;
        exam.setQuestions(answeredQuestions);
        submitExam();
    }

    private void endExam() {
        List<Question> answeredQuestions;
        try {
            answeredQuestions = getSolvedQuestions(false);
        } catch (Exception e) {
            return;
        }
        exam.setQuestions(answeredQuestions);
        submitExam();
        Platform.runLater(() -> {
            ViewFactory.getInstance().showPopUp("Exam Timer Finished", "Exam time has been expired! Your answered questions have been submitted.", PopUpType.INFO);
            ((Stage) examName.getScene().getWindow()).close();
        });
    }

    private void handleCloseRequest(WindowEvent event) {
        List<Question> answeredQuestions;
        try {
            answeredQuestions = getSolvedQuestions(false);
        } catch (Exception e) {
            return;
        }
        boolean hasConfirmed = ViewFactory.getInstance().showPopUp("End The Exam",
                "Are you sure you want to end the exam? Only the answered questions will be submitted.",
                PopUpType.WARNING);

        if (!hasConfirmed) {
            event.consume();
            return;
        }
        exam.setQuestions(answeredQuestions);
        submitExam();
    }

    private List<Question> getSolvedQuestions(boolean stopNotAnswered) throws Exception {
        List<Question> answeredQuestions = new ArrayList<>();
        for (QuestionController controller : questionControllers) {
            answeredQuestions.add(controller.getAnsweredQuestion(stopNotAnswered));
        }
        return answeredQuestions;
    }

    private void submitExam(){
        try {
            examService.submitExam(exam, studentId);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        ((Stage) examName.getScene().getWindow()).close();
    }
}
