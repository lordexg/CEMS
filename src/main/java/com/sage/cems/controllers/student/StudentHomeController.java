package com.sage.cems.controllers.student;

import com.sage.cems.models.Student;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentHomeController implements Initializable {

    public Label welcomeMessage;

    private Student student = new Student();

    public void setStudent(Student student) {
        this.student = student;
        updateHome();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateHome();
    }

    private void updateHome() {
        welcomeMessage.setText("Hello, " + student.getFirstName() + " " + student.getLastName());
    }

}
