package com.sage.cems.controllers.student;

import com.sage.cems.models.Exam;
import com.sage.cems.util.TimeConversion;
import javafx.scene.Camera;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentExamController {
    public Label header;
    public Label examCourseName;
    public Label examDuration;
    public Label examNoOfQuestions;
    public Label examTotalMarks;

    private Exam exam;

    public void setExam(Exam exam) {
        this.exam = exam;
        loadExamInfo();
    }

    private void loadExamInfo() {
        if (exam == null)
            return;
        header.setText(exam.getExamName());
        examCourseName.setText(exam.getCourseID());
        examDuration.setText(TimeConversion.formatMilliseconds(exam.getExamDuration()));
        examNoOfQuestions.setText(String.valueOf(exam.getExamLength()));
        examTotalMarks.setText(String.valueOf((int)exam.getMark()));
    }

//    private String startWarningMessage() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(exam.getExamStartDate());
//        calendar.setTimeInMillis(calendar.getTimeInMillis() + exam.getExamDuration());
//        return String.format(
//                "NOTE: You can only enter the exam between %s and %s.",
//                dateFormat.format(exam.getExamStartDate()), dateFormat.format(calendar.getTime())
//        );
//    }
}
