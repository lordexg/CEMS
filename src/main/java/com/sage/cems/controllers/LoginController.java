package com.sage.cems.controllers;

import com.sage.cems.models.Student;
import com.sage.cems.models.user.User;
import com.sage.cems.services.LoginService;
import com.sage.cems.services.StudentService;
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

    private LoginService loginService;
    private StudentService studentService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loginService = new LoginService();
            studentService = new StudentService();
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
            User user = loginService.login(userNameField.getText(), passwordField.getText());
            ViewFactory.getInstance().closeStage((Stage) loginBtn.getScene().getWindow());
            showUserStage(user);
        } catch (Exception e) {
            errorLabel.setVisible(true);
            errorLabel.setText("Wrong User Name or Password");
        }
    }

    private void showUserStage(User user) {
        switch (user.getRole()) {
            case STUDENT -> {
                try {
                    Student student = studentService.getStudentData(user);
                    ViewFactory.getInstance().showStudentWindow(student);
                } catch (Exception e_) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e_);
                }
            }
            case LECTURER -> ViewFactory.getInstance().showLecturerWindow();
            case ADMIN -> ViewFactory.getInstance().showAdminWindow(user);
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