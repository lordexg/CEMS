package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.util.TimeConversion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static com.sage.cems.util.TimeConversion.calculateRemainingTimeInSeconds;
import static com.sage.cems.util.TimeConversion.formatTime;

public class StudentExamController {
    public Label header;
    public Label examCourseName;
    public Label examDuration;
    public Label examNoOfQuestions;
    public Label examTotalMarks;
    public Label countdownLabel;
    public Label timerHeader;
    public Button startExamButton;

    private Exam exam;

    public void setExam(Exam exam) {
        this.exam = exam;
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
        setCountdownLabel();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.seconds(1), // Update every second
                event -> {
                    if (setCountdownLabel())
                        timeline.stop();
                }
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timeline.play(); // Start the countdown
    }

    private boolean setCountdownLabel() {
        boolean completed = false;
        // Calculate remaining time
        long remainingTimeInSeconds = calculateRemainingTimeInSeconds(exam.getExamStartDate());
        long examDurationInSeconds = TimeConversion.millisecondsToSeconds(exam.getExamDuration());
        if (remainingTimeInSeconds >= 0) {
            timerHeader.setText("YOU CAN START THE EXAM AFTER");
            countdownLabel.setVisible(true);
            countdownLabel.setManaged(true);
            countdownLabel.setText(formatTime(remainingTimeInSeconds));
            startExamButton.setDisable(true);
        }
        else if (Math.abs(remainingTimeInSeconds) <= examDurationInSeconds) {
            timerHeader.setText("THE EXAM WILL BE CLOSED AFTER");
            countdownLabel.setVisible(true);
            countdownLabel.setManaged(true);
            countdownLabel.setText(formatTime(examDurationInSeconds + remainingTimeInSeconds));
            startExamButton.setDisable(false);
        }
        else {
            timerHeader.setText("THE EXAM IS FINISHED");
            countdownLabel.setVisible(false);
            countdownLabel.setManaged(false);
            completed = true;
            startExamButton.setDisable(true);
        }
        return completed;
    }
}
