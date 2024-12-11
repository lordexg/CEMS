package com.sage.cems.controllers.student;

import com.sage.cems.models.Question;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
    public Label correctAnswerField;

    private Question question;
    private int questionNumber;
    private boolean isAnswered;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionParent.setOnMouseClicked( _ -> questionParent.getStyleClass().remove("question-error"));
    }

    public void setQuestionData(Question question, int questionNumber, boolean isAnswered) {
        this.question = question;
        this.questionNumber = questionNumber;
        this.isAnswered = isAnswered;
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
        if (isAnswered) {
            shortAnswerField.setText(question.getStudentAnswer());
            shortAnswerField.setDisable(true);
            if (question.getStudentAnswer().equals(question.getCorrectAnswer())) {
                shortAnswerField.getStyleClass().add("correct-short-answer");
                return;
            }
            shortAnswerField.getStyleClass().add("question-error");
            correctAnswerField.setVisible(true);
            correctAnswerField.setText("Correct Answer: " + question.getCorrectAnswer());
        }
    }

    private void createMCQuestionView() {
        toggleChoicesPane(true);
        Iterator<String> iterator = question.getChoices().iterator();
        for (ToggleButton toggleButton : new ToggleButton[]{firstChoice, secondChoice, thirdChoice, fourthChoice}) {
            toggleButton.setOnMouseClicked(_ -> questionParent.getStyleClass().remove("question-error"));
            toggleButton.setVisible(true);
            toggleButton.setText(iterator.next());
            if (isAnswered)
                consumeToggle(toggleButton);
        }
        if (isAnswered)
            showCorrectedChoices();
    }

    private void createTrueFalseQuestionView() {
        toggleChoicesPane(true);
        firstChoice.setText(question.getChoices().getFirst());
        secondChoice.setText(question.getChoices().getLast());
        thirdQuestionPane.setVisible(false);
        fourthQuestionPane.setVisible(false);
        thirdQuestionPane.setManaged(false);
        fourthQuestionPane.setManaged(false);
        if (isAnswered) {
            showCorrectedChoices();
            consumeToggle(firstChoice);
            consumeToggle(secondChoice);
        }

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

    private void showCorrectedChoices() {
        boolean isCorrect = question.getCorrectAnswer().equals(question.getStudentAnswer());
        for (ToggleButton toggleButton : new ToggleButton[]{firstChoice, secondChoice, thirdChoice, fourthChoice}) {
            if (isCorrect && toggleButton.getId().equals(question.getCorrectAnswer())) {
                toggleButton.getStyleClass().add("green-choice-answer");
                return;
            }
            if (!isCorrect && toggleButton.getId().equals(question.getStudentAnswer()))
                toggleButton.getStyleClass().add("red-choice-answer");
            if (!isCorrect && toggleButton.getId().equals(question.getCorrectAnswer()))
                toggleButton.getStyleClass().add("green-choice-answer");
        }
    }

    private void consumeToggle(ToggleButton toggleButton) {
        toggleButton.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        toggleButton.getStyleClass().add("consume-toggle");
    }

}
