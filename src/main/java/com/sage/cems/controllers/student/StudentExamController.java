package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.services.ExamService;
import com.sage.cems.util.TimeConversion;
import com.sage.cems.views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Exam exam;
    private String studentId;
    private ExamService examService;

    public StudentExamController() {
        try {
            this.examService = new ExamService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startExamButton.setOnAction( _ -> onStart());
    }

    public void onStart() {
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
        boolean completed = false;
        try {
            completed = examService.isExamCompleted(exam, studentId);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        // Calculate remaining time
        long remainingTimeInSeconds = calculateRemainingTimeInSeconds(exam.getExamStartDate());
        long examDurationInSeconds = TimeConversion.millisecondsToSeconds(exam.getExamDuration());

        if (completed) {
            timerHeader.setText("THE EXAM IS FINISHED");
            countdownLabel.setVisible(false);
            countdownLabel.setManaged(false);
            startExamButton.setDisable(true);
            return true;
        }

        if (remainingTimeInSeconds >= 0) {
            timerHeader.setText("YOU CAN START THE EXAM AFTER");
            countdownLabel.setVisible(true);
            countdownLabel.setManaged(true);
            countdownLabel.setText(formatTime(remainingTimeInSeconds));
            startExamButton.setDisable(true);
        }
        else {
            timerHeader.setText("THE EXAM WILL BE CLOSED AFTER");
            countdownLabel.setVisible(true);
            countdownLabel.setManaged(true);
            countdownLabel.setText(formatTime(examDurationInSeconds + remainingTimeInSeconds));
            startExamButton.setDisable(false);
        }

        return false;
    }
}
