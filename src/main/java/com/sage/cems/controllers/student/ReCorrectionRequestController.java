package com.sage.cems.controllers.student;

import com.sage.cems.models.Grade;
import com.sage.cems.models.ReCorrection;
import com.sage.cems.services.ReCorrectionService;
import com.sage.cems.views.PopUpType;
import com.sage.cems.views.ViewFactory;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReCorrectionRequestController implements Initializable {
    public TextField courseField;
    public TextField examNameField;
    public TextArea messageTextArea;
    public Button submitButton;

    private final Grade grade;
    private final BooleanProperty hasSubmittedRequestProperty;
    private final ReCorrectionService reCorrectionService;

    public ReCorrectionRequestController(Grade grade, ReCorrectionService reCorrectionService, BooleanProperty hasSubmittedRequestProperty) {
        this.grade = grade;
        this.hasSubmittedRequestProperty = hasSubmittedRequestProperty;
        this.reCorrectionService = reCorrectionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseField.setText(grade.getCourseID());
        examNameField.setText(grade.getExamName());
        submitButton.setOnAction( _ -> onSubmit());
        Platform.runLater(() -> courseField.getScene().getWindow().setOnCloseRequest(this::handleCloseRequest));
        messageTextArea.textProperty().addListener((_, _, newVal) -> submitButton.setDisable(newVal.isEmpty()));
    }

    private void onSubmit() {
        submitReCorrectionRequest();
        ViewFactory.getInstance().showPopUp("Request has been submitted", "Your re-correction request has been submitted successfully.", PopUpType.INFO);
        ((Stage) courseField.getScene().getWindow()).close();
        hasSubmittedRequestProperty.set(true);
    }

    private void handleCloseRequest(WindowEvent event) {
        if (messageTextArea.getText().isEmpty()) {
            return;
        }
        boolean hasConfirmed = ViewFactory.getInstance().showPopUp("Discard Re-Correction Request",
                "Do you want to discard the message you have written?",
                PopUpType.WARNING);
        if (!hasConfirmed)
            event.consume();
    }

    private void submitReCorrectionRequest() {
        ReCorrection reCorrection = new ReCorrection(grade.getExamID(), grade.getStudentID(),
                messageTextArea.getText(), false);
        try {
            reCorrectionService.submitReCorrection(reCorrection);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}
