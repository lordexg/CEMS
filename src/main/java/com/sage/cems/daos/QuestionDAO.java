package com.sage.cems.daos;
import com.sage.cems.models.Question;
import com.sage.cems.models.QuestionType;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;
import java.io.IOException;
import java.util.*;

public class QuestionDAO {
    private final FileManager fileManager;

    public QuestionDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * @param examID
     * @return List of the questions of the give exam ID.
     */
    public List<Question> getQuestions(String examID) throws IOException {
        // In fact this function takes any column value of QUESTION_TABLE due to getRows() nature.
        // the ID of an exam might conflict with a questions index or any other column's value.
        // I have to filter the list based on the value of ColumnName only (get correct question's ExamID)!
        List<Map<ColumnName, String>> matchedQuestions = fileManager.getRows(TableName.QUESTION, examID);
        List<Map<ColumnName, String>> examQuestions = new ArrayList<>();
        for(Map<ColumnName, String> questions : matchedQuestions) {
            if(Objects.equals(questions.get(ColumnName.EXAM_ID), examID)){
                examQuestions.add(questions);
            };
        }

        if (examQuestions.isEmpty()) {
            throw new IOException("No questions found");
        }

        return createQuestionsList(examQuestions);
    }
    public void addQuestion(Question question) throws IOException {
        fileManager.insertRow(TableName.QUESTION, createQuestionMap(question));
    }

//     NOT WORKING (UPDATE HAS NO EFFECT ON QUESTION_TABLE)
    public void updateQuestion(Question question) throws IOException {
        fileManager.updateRow(TableName.QUESTION, createQuestionMap(question));
    }

    public void deleteQuestion(Question question) throws IOException {
        fileManager.deleteRow(TableName.QUESTION, createQuestionMap(question));
    }
//===========================================================================================
//===========================================================================================
//===========================================================================================
//===========================================================================================
    private Question createQuestion(Map<ColumnName, String> questionMap) throws IOException {
        Question question = new Question();
        populateQuestionFields(question, questionMap);
        return question;
    }

    private Map<ColumnName, String> createQuestionMap(Question question) throws IOException {
        Map<ColumnName, String> newQuestion = new TreeMap<>();

        // to put choices in the QUESTIONS_TABLE as choice1,choice2,choice3... or null
        List<String> choicesList;
        String choices;
        try{
            choicesList = question.getChoices();
            choices = String.join(",", choicesList);
        }catch (NullPointerException e){
            choices = "-";
        }

        newQuestion.put(ColumnName.QUESTION_CHOICES, choices);
        newQuestion.put(ColumnName.EXAM_ID, question.getExamID());
        newQuestion.put(ColumnName.QUESTION_STATEMENT, question.getStatement());
        newQuestion.put(ColumnName.QUESTION_TYPE, question.getQuestionType().name());
        newQuestion.put(ColumnName.QUESTION_ID, question.getQuestionID());
        newQuestion.put(ColumnName.QUESTION_CORRECT_ANSWER, question.getCorrectAnswer());
        //newQuestion.put(ColumnName.QUESTION_STUDENT_ANSWER, question.getStudentAnswer());

        return newQuestion;
    }

    private List<Question> createQuestionsList(List<Map<ColumnName, String>> questionsMap) throws IOException {
        List<Question> questionList = new ArrayList<>();
        for (Map<ColumnName, String> question : questionsMap) {
            questionList.add(createQuestion(question));
        }
        return questionList;
    }

    private void populateQuestionFields(Question question, Map<ColumnName, String> questionMap) throws IOException {
        question.setExamID(questionMap.get(ColumnName.EXAM_ID));
        question.setQuestionID(String.valueOf(questionMap.get(ColumnName.QUESTION_ID)));
        question.setQuestionType(QuestionType.valueOf(questionMap.get(ColumnName.QUESTION_TYPE)));
        question.setStatement(questionMap.get(ColumnName.QUESTION_STATEMENT));
        question.setChoices(Collections.singletonList(questionMap.get(ColumnName.QUESTION_CHOICES)));
        question.setCorrectAnswer(questionMap.get(ColumnName.QUESTION_CORRECT_ANSWER));
        //question.setStudentAnswer(questionMap.get(ColumnName.QUESTION_STUDENT_ANSWER));
    }

}