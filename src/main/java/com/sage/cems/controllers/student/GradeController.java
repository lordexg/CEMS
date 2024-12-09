package com.sage.cems.controllers.student;

import com.sage.cems.models.Grade;
import com.sage.cems.services.GradeService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GradeController {

    public Label examNameLabel;
    public Label courseIdLabel;
    public Label gradeLabel;
    public Label totalGradeLabel;
    public Button checkAnswersButton;
    public Button moreButton;

    private Grade grade;
    private GradeService gradeService;

    public void setGradeData(Grade grade, GradeService gradeService) {
        this.grade = grade;
        this.gradeService = gradeService;
        updateView();
    }

    private void updateView() {
        examNameLabel.setText(grade.getExamName());
        courseIdLabel.setText(grade.getCourseID());
        gradeLabel.setText(grade.getMark() + " (" + gradeService.getGradeSymbol(grade) + ")");
        totalGradeLabel.setText(grade.getFullMark() + "");
        checkAnswersButton.setOnAction( _ -> onCheckAnswers());
        moreButton.setOnAction( _ -> onMore());
    }

    private void onCheckAnswers() {

    }

    private void onMore() {

    }

}
