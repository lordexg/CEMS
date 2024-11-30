package com.sage.cems.controllers.student;

import com.sage.cems.models.Student;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public BorderPane mainView;
    private final Student student;

    public StudentController(Student student) {
        this.student = student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainView.setCenter(ViewFactory.getInstance().getView(View.STUDENT_HOME));
        passStudentToHomeController();

        ViewFactory.getInstance().getStudentSelectedMenuBtn().addListener((observableValue, oldVal, newVal) -> {
            mainView.setCenter(ViewFactory.getInstance().getView(newVal));
            switch (newVal) {
                case STUDENT_HOME -> passStudentToHomeController();
                case STUDENT_COURSES -> {
                }
                case STUDENT_RESULTS -> {
                }
                case STUDENT_PROFILE -> {
                }
            }
        });
    }

    private void passStudentToHomeController() {
        StudentHomeController homeController = (StudentHomeController) ViewFactory.getInstance().getController(View.STUDENT_HOME);
        homeController.setStudent(student);
    }
}
