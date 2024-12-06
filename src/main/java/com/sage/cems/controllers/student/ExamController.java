package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.services.ExamService;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExamController implements Initializable {
    public AnchorPane examParent;
    public ImageView examImage;
    public Label examName;
    public Label examDate;
    public ImageView completedMark;

    private Exam exam;
    private String studentId;
    private ExamService examService;

    public ExamController() {
        try {
            this.examService = new ExamService();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setExamData(Exam exam, String studentId) {
        this.exam = exam;
        this.studentId = studentId;
        examName.setText(exam.getExamName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        examDate.setText(dateFormat.format(exam.getExamStartDate()));

        try {
            completedMark.setVisible(examService.isExamCompleted(exam, studentId));
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examParent.setOnMouseClicked(_ -> onExamClicked());
    }

    private void onExamClicked() {
        ViewFactory.getInstance().getBackStack().push(View.STUDENT_COURSE_EXAMS);
        ViewFactory.getInstance().backStackSizeProperty().set(ViewFactory.getInstance().getBackStack().size());
        ViewFactory.getInstance().getCurrentViewProperty().set(View.STUDENT_EXAM);
        StudentExamController controller = (StudentExamController) ViewFactory.getInstance().getController(View.STUDENT_EXAM);
        controller.setExamData(exam, studentId);
    }
}
