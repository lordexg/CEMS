package com.sage.cems.controllers;

import com.sage.cems.views.PopUpType;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PopUpController implements Initializable {
    public ImageView icon;
    public Label headerLabel;
    public Label messageLabel;
    public Button confirmButton;
    public Button cancelButton;

    private final String header;
    private final String message;
    private final PopUpType popUpType;
    private final AtomicBoolean popUpResult;

    public PopUpController(String header, String message, PopUpType popUpType, AtomicBoolean popUpResult) {
        this.header = header;
        this.message = message;
        this.popUpType = popUpType;
        this.popUpResult = popUpResult;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(header);
        messageLabel.setText(message);
        if (popUpType.equals(PopUpType.WARNING))
            icon.setImage(new Image(String.valueOf(getClass().getResource("/images/Warning-large.png"))));

        if (popUpType.equals(PopUpType.INFO)) {
            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        }
        confirmButton.setOnAction( _ -> {
            popUpResult.set(true);
            ((Stage) icon.getScene().getWindow()).close();
        });
        cancelButton.setOnAction( _ -> {
            popUpResult.set(false);
            ((Stage) icon.getScene().getWindow()).close();
        });
    }
}
