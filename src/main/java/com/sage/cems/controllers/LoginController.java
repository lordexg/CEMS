package com.sage.cems.controllers;

import com.sage.cems.models.user.User;
import com.sage.cems.services.LoginService;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    public TextField userNameField;
    public PasswordField passwordField;
    public Button loginBtn;
    public Button infoBtn;
    public Label errorLabel;

    LoginService loginService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loginService = new LoginService();
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
        cancelErrorLabelListeners();
        loginBtn.setOnAction(actionEvent -> onLogin());
        infoBtn.setOnAction(actionEvent -> onInfo());
    }

    private void onLogin() {
        try {
            System.out.println(userNameField.getText() + "\t" + passwordField.getText());
            User user = loginService.login(userNameField.getText(), passwordField.getText());
            ViewFactory.getInstance().closeStage((Stage) loginBtn.getScene().getWindow());
            ViewFactory.getInstance().showStudentWindow();
        } catch (Exception e) {
            errorLabel.setVisible(true);
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            errorLabel.setText("Wrong User Name or Password");
        }
    }

    private void onInfo() {
        try {
            loginService.showDocumentationPage();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void cancelErrorLabelListeners() {
        userNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
        });
    }
}