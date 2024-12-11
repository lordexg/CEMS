package com.sage.cems.controllers;

import com.sage.cems.models.user.User;
import com.sage.cems.services.UserService;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileController implements Initializable {
    public ImageView studentImageLabel;
    public Label userNameLabel;
    public Label userRoleLabel;
    public TextField idField;
    public TextField passwordField;
    public TextField firstNameFiled;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneNumberField;
    public AnchorPane activeField = null;

    private User user;
    private UserService userService;

    public ProfileController() {
        try {
            this.userService = new UserService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setUser(User user) {
        this.user = user;
        updateView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> idField.getScene().setOnMouseClicked(mouseEvent -> {
            if (activeField == null)
                return;
            if (mouseEvent.getTarget() != activeField) {
                unFocusActiveField();
            }
        }));
    }

    private void updateView() {
        if (user == null)
            return;
        userNameLabel.setText(user.getFirstName());
        userRoleLabel.setText(user.getRole().name().charAt(0) + user.getRole().name().toLowerCase());
        idField.setText(user.getID());
        updateFields();
        handleEditingFields();
    }

    private void handleEditingFields() {
        List<TextField> editableFields = List.of(passwordField, firstNameFiled, lastNameField, emailField, phoneNumberField);
        for (TextField field : editableFields) {
            AnchorPane container = (AnchorPane) field.getParent();
            Button editButton = (Button) container.getChildren().getFirst();
            editButton.setOnAction( _ -> {
                if (editButton.getText().equals("Edit")) {
                    field.setDisable(false);
                    field.requestFocus();
                    activeField = container;
                } else {
                    updateUser();
                    updateFields();
                    unFocusActiveField();
                    updateView();
                    updateFiles();
                }
            });
            field.textProperty().addListener((_, _, newVal) -> {
                if (newVal.equals(getFieldOldValue(field)))
                    editButton.setText("Edit");
                else
                    editButton.setText("Save");
            });
        }
    }

    private void updateUser() {
        user.setPassword(passwordField.getText());
        user.setFirstName(firstNameFiled.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setPhoneNumber(phoneNumberField.getText());
    }

    private void updateFields() {
        passwordField.setText(user.getPassword());
        firstNameFiled.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        phoneNumberField.setText(user.getPhoneNumber());
    }

    private String getFieldOldValue(TextField textField) {
        if (textField.equals(passwordField))
            return user.getPassword();
        else if (textField.equals(firstNameFiled))
            return user.getFirstName();
        else if (textField.equals(lastNameField))
            return user.getLastName();
        else if (textField.equals(emailField))
            return user.getEmail();
        else {
            return user.getPhoneNumber();
        }
    }

    private void unFocusActiveField() {
        activeField.getParent().requestFocus();
        ((Button) activeField.getChildren().getFirst()).setText("Edit");
        updateFields();
        activeField.getChildren().get(2).setDisable(true);
        activeField = null;
    }

    private void updateFiles() {
        try {
            userService.updateUser(user);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

}
