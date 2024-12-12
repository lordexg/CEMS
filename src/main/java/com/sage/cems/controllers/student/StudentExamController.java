package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.services.ExamService;
import com.sage.cems.services.GradeService;
import com.sage.cems.services.ReCorrectionService;
import com.sage.cems.util.TimeConversion;
import com.sage.cems.views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sage.cems.util.TimeConversion.calculateRemainingTimeInSeconds;
import static com.sage.cems.util.TimeConversion.formatTime;

public class StudentExamController implements Initializable {
    public Label header;
    public Label examCourseName;
    public Label examDuration;
    public Label examNoOfQuestions;
    public Label examTotalMarks;
    public Label countdownLabel;
    public Label timerHeader;
    public Button startExamButton;
    public StackPane afterExamPane;
    public Button giveFeedbackButton;
    public Button showAnswersButton;

    private final BooleanProperty examCompletedProperty = new SimpleBooleanProperty();
    private Exam exam;
    private String studentId;
    private ExamService examService;
    private GradeService gradeService;
    private ReCorrectionService reCorrectionService;

    public StudentExamController() {
        try {
            this.examService = new ExamService();
            this.gradeService = new GradeService();
            this.reCorrectionService = new ReCorrectionService();

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startExamButton.setOnAction( _ -> onStart());
        showAnswersButton.setOnAction( _ -> onShowAnswersButton());
        giveFeedbackButton.setOnAction( _ -> onGiveFeedbackButton());
    }

    private void onStart() {
        header.getScene().getWindow().hide();
        ViewFactory.getInstance().showExamWindow(exam, studentId, ((Stage) header.getScene().getWindow()));
    }

    public void setExamData(Exam exam, String studentId) {
        this.exam = exam;
        this.studentId = studentId;
        loadExamInfo();
    }

    private void loadExamInfo() {
        if (exam == null)
            return;
        header.setText(exam.getExamName());
        examCourseName.setText(exam.getCourseID());
        examDuration.setText(TimeConversion.formatMilliseconds(exam.getExamDuration()));
        examNoOfQuestions.setText(String.valueOf(exam.getExamLength()));
        examTotalMarks.setText(String.valueOf((int)exam.getFullMark()));
        handleAfterExamButton();
        startCountDownTimer();
    }

    private void startCountDownTimer() {
        updateCountdownLabel();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1), // Update every second
                    _ -> {
                    if (updateCountdownLabel())
                        timeline.stop();
                }
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timeline.play(); // Start the countdown
    }

    private boolean updateCountdownLabel() {
        boolean completed = isExamCompleted();
        examCompletedProperty.set(completed);
        // Calculate remaining time
        long remainingTimeInSeconds = calculateRemainingTimeInSeconds(exam.getExamStartDate());
        long examDurationInSeconds = TimeConversion.millisecondsToSeconds(exam.getExamDuration());

        if (completed) {
            timerHeader.setText("THE EXAM IS FINISHED");
            countdownLabel.setText("Waiting for admin approval...");
            countdownLabel.setVisible(!exam.isApproved());
            countdownLabel.setManaged(!exam.isApproved());
            startExamButton.setDisable(true);
            startExamButton.setVisible(false);
            return true;
        }

        if (remainingTimeInSeconds >= 0) {
            timerHeader.setText("YOU CAN START THE EXAM AFTER");
            startExamButton.setDisable(true);
            countdownLabel.setText(formatTime(remainingTimeInSeconds));
        }
        else {
            timerHeader.setText("THE EXAM WILL BE CLOSED AFTER");
            startExamButton.setDisable(false);
            countdownLabel.setText(formatTime(examDurationInSeconds + remainingTimeInSeconds));
        }
        countdownLabel.setVisible(true);
        countdownLabel.setManaged(true);
        startExamButton.setVisible(true);

        return false;
    }

    private boolean isExamCompleted() {
        try {
            return examService.isExamCompleted(exam, studentId);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    private void handleAfterExamButton() {
        afterExamPane.setVisible(isExamCompleted());
        examCompletedProperty.addListener((_, _, newVal) -> afterExamPane.setVisible(newVal));
        showAnswersButton.setDisable(!exam.isApproved());
    }

    private void onShowAnswersButton() {
        BooleanProperty hasSubmittedRequestProperty = new SimpleBooleanProperty();
        Exam solvedExam = null;
        try {
            solvedExam = gradeService.getStudentSolvedExam(exam.getExam_ID(), studentId);
            hasSubmittedRequestProperty.set(reCorrectionService.hasRequestReCorrection(studentId, exam.getExam_ID()));
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        header.getScene().getWindow().hide();
        ViewFactory.getInstance().showCorrectedExamWindow(solvedExam, studentId, hasSubmittedRequestProperty, ((Stage) header.getScene().getWindow()), gradeService, reCorrectionService);
    }

    private void onGiveFeedbackButton() {

    }
}
