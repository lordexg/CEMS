package com.sage.cems.controllers.student;

import com.sage.cems.models.Course;
import com.sage.cems.models.Exam;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamController implements Initializable {
    public AnchorPane examParent;
    public ImageView examImage;
    public Label examName;

    private Exam exam;

    public void setCourseImage(Image image) {
        examImage.setImage(image);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        examName.setText(exam.getExamName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examParent.setOnMouseClicked(mouseEvent -> onExamClicked());
    }

    private void onExamClicked() {

    }
}
