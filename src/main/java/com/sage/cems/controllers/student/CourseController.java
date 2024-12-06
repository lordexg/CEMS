package com.sage.cems.controllers.student;

import com.sage.cems.models.Course;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    public ImageView courseImage;
    public Label courseName;
    public AnchorPane courseParent;

    private Course course;
    private String studentId;

    public void setCourseImage(Image image) {
        courseImage.setImage(image);
    }

    public void setCourseData(Course course, String studentId) {
        this.course = course;
        this.studentId = studentId;
        courseName.setText(course.getCourseName() + " (" + course.getCourseID() + ")");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseParent.setOnMouseClicked(_ -> onCourseClicked());
    }

    private void onCourseClicked() {
        ViewFactory.getInstance().getBackStack().push(View.STUDENT_COURSES);
        ViewFactory.getInstance().backStackSizeProperty().set(ViewFactory.getInstance().getBackStack().size());
        ViewFactory.getInstance().getCurrentViewProperty().set(View.STUDENT_COURSE_EXAMS);
        StudentCourseExamsController controller = (StudentCourseExamsController) ViewFactory.getInstance().getController(View.STUDENT_COURSE_EXAMS);
        controller.setCourseData(course, studentId);
    }
}
