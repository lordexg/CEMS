package com.sage.cems.controllers.student;

import com.sage.cems.models.Course;
import com.sage.cems.models.Student;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentCoursesController implements Initializable {

    public TilePane coursesPane;
    public Label noCoursesMessage;

    private Student student = null;

    public void setStudent(Student student) {
        this.student = student;
        loadCourses();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCourses();
    }

    private void loadCourses() {
//        System.out.println(student.getFirstName());
        if (student == null)
            return;
        coursesPane.getChildren().clear();
        noCoursesMessage.setVisible(false);
        List<Course> courses = student.getCourses();
        if (courses.isEmpty()) {
            noCoursesMessage.setVisible(true);
            return;
        }
        for (Course course : courses) {
            coursesPane.getChildren().add(generateCourseView(course));
        }
    }

    private AnchorPane generateCourseView(Course course) {
        AnchorPane courseView = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/course.fxml"));
            courseView = loader.load();
            ((CourseController)loader.getController()).setCourseName(course.getCourseName());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return courseView;
    }
}
