package com.sage.cems.controllers.student;

import com.sage.cems.models.Student;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public BorderPane mainView;
    public Button backBtn;
    private final Student student;

    public StudentController(Student student) {
        this.student = student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainView.setCenter(ViewFactory.getInstance().getView(View.STUDENT_HOME));
        passStudentToHomeController();

        ViewFactory.getInstance().getCurrentViewProperty().addListener((_, _, newVal) -> {
            switchView(newVal);
        });

        backBtn.setOnAction( _ -> onBack());

        ViewFactory.getInstance().backStackSizeProperty().addListener((_, _, newVal) -> {
            backBtn.setVisible(!newVal.equals(0));
        });
    }

    private void passStudentToHomeController() {
        StudentHomeController homeController = (StudentHomeController) ViewFactory.getInstance().getController(View.STUDENT_HOME);
        homeController.setStudent(student);
    }

    private void switchView(View view) {
        mainView.setCenter(ViewFactory.getInstance().getView(view));
        switch (view) {
            case STUDENT_HOME -> passStudentToHomeController();
            case STUDENT_COURSES -> {
                StudentCoursesController coursesController = (StudentCoursesController) ViewFactory.getInstance().getController(View.STUDENT_COURSES);
                coursesController.setStudent(student);
            }
            case STUDENT_RESULTS -> {
                StudentResultsController resultsController = (StudentResultsController) ViewFactory.getInstance().getController(View.STUDENT_RESULTS);
                resultsController.setStudentID(student.getID());
            }
            case STUDENT_PROFILE -> {
            }
        }
    }

    private void onBack() {
        View view = ViewFactory.getInstance().getBackStack().pop();
        ViewFactory.getInstance().backStackSizeProperty().set(ViewFactory.getInstance().getBackStack().size());
        ViewFactory.getInstance().getCurrentViewProperty().set(view);
    }
}
