package com.sage.cems.controllers.student;

import com.sage.cems.models.Question;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    public AnchorPane questionParent;
    public Label questionNum;
    public Label questionStatement;
    public TilePane choicesPane;
    public ToggleGroup choices;
    public ToggleButton firstChoice;
    public ToggleButton secondChoice;
    public ToggleButton thirdChoice;
    public ToggleButton fourthChoice;
    public HBox shortAnswerPane;
    public TextField shortAnswerField;
    public AnchorPane thirdQuestionPane;
    public AnchorPane fourthQuestionPane;

    private Question question;
    private int questionNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionParent.setOnMouseClicked( _ -> questionParent.getStyleClass().remove("question-error"));
    }

    public void setQuestionData(Question question, int questionNumber) {
        this.question = question;
        this.questionNumber = questionNumber;
        updateView();
    }

    public Question getAnsweredQuestion(boolean stopNotAnswered) throws Exception {
        return switch (question.getQuestionType()) {
            case COMPLETE -> getCompleteQuestion(stopNotAnswered);
            case MCQ, TRUE_FALSE -> getChoiceQuestion(stopNotAnswered);
        };
    }

    private void updateView() {
        questionNum.setText(String.valueOf(questionNumber));
        questionStatement.setText(question.getStatement());
        switch (question.getQuestionType()) {
            case COMPLETE -> createCompleteQuestionView();
            case MCQ -> createMCQuestionView();
            case TRUE_FALSE -> createTrueFalseQuestionView();
        }
    }

    private void createCompleteQuestionView() {
        toggleChoicesPane(false);
        shortAnswerField.setOnMouseClicked(_ -> questionParent.getStyleClass().remove("question-error"));
    }

    private void createMCQuestionView() {
        toggleChoicesPane(true);
        Iterator<String> iterator = question.getChoices().iterator();
        for (ToggleButton toggleButton : new ToggleButton[]{firstChoice, secondChoice, thirdChoice, fourthChoice}) {
            toggleButton.setOnMouseClicked(_ -> questionParent.getStyleClass().remove("question-error"));
            toggleButton.setVisible(true);
            toggleButton.setText(iterator.next());
        }
    }

    private void createTrueFalseQuestionView() {
        toggleChoicesPane(true);
        firstChoice.setText(question.getChoices().getFirst());
        secondChoice.setText(question.getChoices().getLast());
        thirdQuestionPane.setVisible(false);
        fourthQuestionPane.setVisible(false);
        thirdQuestionPane.setManaged(false);
        fourthQuestionPane.setManaged(false);
    }

    private void toggleChoicesPane(boolean visible) {
        choicesPane.setVisible(visible);
        choicesPane.setManaged(visible);
        shortAnswerPane.setVisible(!visible);
        shortAnswerPane.setManaged(!visible);
    }

    private Question getCompleteQuestion(boolean stopNotAnswered) throws Exception {
        String answer = shortAnswerField.getText();
        if (stopNotAnswered && answer.isEmpty()) {
            questionParent.getStyleClass().add("question-error");
            throw new Exception();
        }
        question.setStudentAnswer(answer);
        return question;
    }

    private Question getChoiceQuestion(boolean stopNotAnswered) throws Exception {
        ToggleButton selectedAnswer = (ToggleButton) choices.getSelectedToggle();
        if (stopNotAnswered && selectedAnswer == null) {
            questionParent.getStyleClass().add("question-error");
            throw new Exception();
        }
        question.setStudentAnswer(selectedAnswer == null ? "" : selectedAnswer.getId());
        return question;
    }
}