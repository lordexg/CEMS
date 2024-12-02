package com.sage.cems.daos;

import com.sage.cems.models.Question;
import com.sage.cems.models.QuestionType;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDAOTest {

    @Test
    void getQuestions() throws IOException {
        QuestionDAO questionDAO = new QuestionDAO(new CEMSFileManager());
        List<Question> questionList = questionDAO.getQuestions("1");
        for (Question question : questionList) {
            System.out.println(question.getStatement());
        }
    }

    @Test
    void addQuestions() throws IOException {
        QuestionDAO questionDAO = new QuestionDAO(new CEMSFileManager());
        List<String> choices = Arrays.asList("apple", "banana", "cherry");
        Question question = new Question();
        question.setIndex(2);
        question.setExamID("1");
        question.setStatement("test?");
        question.setCorrectAnswer("test");
        question.setQuestionType(QuestionType.COMPLETE);
        question.setChoices(choices);
        questionDAO.addQuestion(question);
    }

    @Test
    void updateQuestion() throws IOException {
        QuestionDAO questionDAO = new QuestionDAO(new CEMSFileManager());
        List<String> choices = Arrays.asList("apple", "banana", "cherry");
        Question question = new Question();
        question.setIndex(2);
        question.setExamID("1");
        question.setStatement("test?");
        question.setCorrectAnswer("tesWWWWWWWWWWWWWWt");
        question.setQuestionType(QuestionType.COMPLETE);
        questionDAO.updateQuestion(question);
    }

    @Test
    void deleteQuestion() throws IOException {
        QuestionDAO questionDAO = new QuestionDAO(new CEMSFileManager());
        List<String> choices = Arrays.asList("apple", "banana", "cherry");
        Question question = new Question();
        question.setIndex(2);
        question.setExamID("1");
        question.setStatement("test?");
        question.setCorrectAnswer("test");
        question.setQuestionType(QuestionType.COMPLETE);
        question.setChoices(choices);
        questionDAO.deleteQuestion(question);
    }
}