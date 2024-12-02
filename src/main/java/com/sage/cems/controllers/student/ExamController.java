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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ExamController implements Initializable {
    public AnchorPane examParent;
    public ImageView examImage;
    public Label examName;
    public Label examDate;
    public ImageView completedMark;

    private Exam exam;

    public void setExamImage(Image image) {
        examImage.setImage(image);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        examName.setText(exam.getExamName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        examDate.setText(dateFormat.format(exam.getExamStartDate()));
        completedMark.setVisible(exam.isCompleted());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examParent.setOnMouseClicked(mouseEvent -> onExamClicked());
    }

    private void onExamClicked() {
        ViewFactory.getInstance().getBackStack().push(View.STUDENT_COURSE_EXAMS);
        ViewFactory.getInstance().backStackSizeProperty().set(ViewFactory.getInstance().getBackStack().size());
        ViewFactory.getInstance().getCurrentViewProperty().set(View.STUDENT_EXAM);
        StudentExamController controller = (StudentExamController) ViewFactory.getInstance().getController(View.STUDENT_EXAM);
        controller.setExam(exam);
    }
}
