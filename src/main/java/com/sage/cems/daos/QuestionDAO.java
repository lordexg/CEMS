package com.sage.cems.daos;
import com.sage.cems.models.Question;
import com.sage.cems.models.QuestionType;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.EnumHelper;
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
            return new ArrayList<>();
        }

        return createQuestionsList(examQuestions);
    }

    public void addQuestion(Question question) throws IOException {
        fileManager.insertRow(TableName.QUESTION, createQuestionMapADD(question));
    }

//     NOT WORKING (UPDATE HAS NO EFFECT ON QUESTION_TABLE)
    public void updateQuestion(Question question) throws IOException {
        fileManager.updateRow(TableName.QUESTION, createQuestionMapDU(question));
    }

    public void deleteQuestion(Question question) throws IOException {
        fileManager.deleteRow(TableName.QUESTION, createQuestionMapDU(question));
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

    private Map<ColumnName, String> createQuestionMapADD(Question question) throws IOException {
        Map<ColumnName, String> newQuestion = new TreeMap<>();
        newQuestion.put(ColumnName.QUESTION_ID, EnumHelper.getNewPK(TableName.QUESTION));
        populateQuestionMap(newQuestion, question);
        return newQuestion;
    }
    // used for deletion/update, MUST ADD QUESTION_ID
    private Map<ColumnName, String> createQuestionMapDU(Question question) throws IOException {
        Map<ColumnName, String> newQuestion = new TreeMap<>();
        newQuestion.put(ColumnName.QUESTION_ID, question.getQuestionID());
        populateQuestionMap(newQuestion, question);
        return newQuestion;
    }

    /**
     *  populates an questionMap without adding an questionID, cus different createQuestionMap functions will implement it
     */
    private void populateQuestionMap(Map<ColumnName, String> questionMap, Question question) throws IOException {
        // to put choices in the QUESTIONS_TABLE as choice1,choice2,choice3... or null
        List<String> choicesList;
        String choices;
        try{
            choicesList = question.getChoices();
            choices = String.join(",,,", choicesList);
        }catch (NullPointerException e){
            choices = "-";
        }

        questionMap.put(ColumnName.QUESTION_CHOICES, choices);
        questionMap.put(ColumnName.EXAM_ID, question.getExamID());
        questionMap.put(ColumnName.QUESTION_STATEMENT, question.getStatement());
        questionMap.put(ColumnName.QUESTION_TYPE, question.getQuestionType().name());
        questionMap.put(ColumnName.QUESTION_CORRECT_ANSWER, question.getCorrectAnswer());
    }

    private List<Question> createQuestionsList(List<Map<ColumnName, String>> questionsMap) throws IOException {
        List<Question> questionList = new ArrayList<>();
        for (Map<ColumnName, String> question : questionsMap) {
            questionList.add(createQuestion(question));
        }
        return questionList;
    }

    private void populateQuestionFields(Question question, Map<ColumnName, String> questionMap) throws IOException {
        String choices = questionMap.get(ColumnName.QUESTION_CHOICES);
        List<String> choicesList = List.of(choices.split(",,,"));

        question.setChoices(choicesList);
        question.setExamID(questionMap.get(ColumnName.EXAM_ID));
        question.setQuestionID(String.valueOf(questionMap.get(ColumnName.QUESTION_ID)));
        question.setQuestionType(QuestionType.valueOf(questionMap.get(ColumnName.QUESTION_TYPE)));
        question.setStatement(questionMap.get(ColumnName.QUESTION_STATEMENT));
        question.setCorrectAnswer(questionMap.get(ColumnName.QUESTION_CORRECT_ANSWER));
    }

}