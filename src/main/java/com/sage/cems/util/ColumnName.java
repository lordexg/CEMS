package com.sage.cems.util;

public enum ColumnName {
    GRADE_ID,
    QUESTION_ID,
    EXAM_ID,
    // Student table
    STUDENT_ID,
    STUDENT_FIRST_NAME,
    STUDENT_LAST_NAME,
    STUDENT_EMAIL,
    STUDENT_PHONE_NUMBER,
    // Lecturer table
    LECTURER_ID,
    LECTURER_FIRST_NAME,
    LECTURER_LAST_NAME,
    LECTURER_EMAIL,
    LECTURER_PHONE_NUMBER,
    // Accounts table
    ACCOUNT_ID,
    ACCOUNT_PASSWORD,
    ACCOUNT_ROLE,
    // Course table
    COURSE_ID,
    COURSE_NAME,
    // Exam table
    EXAM_NAME,
    EXAM_LENGTH,
    EXAM_DURATION,
    EXAM_START_DATE,
    EXAM_FULL_MARK,
    EXAM_IS_APPROVED,
    EXAM_IS_COMPLETED,
    EXAM_QUESTIONS,
    // Solved exams table
    SOLVED_EXAM_ANSWERS,
    // Questions
    QUESTION_STATEMENT,
    QUESTION_CORRECT_ANSWER,
    QUESTION_TYPE,
    QUESTION_CHOICES,
    QUESTION_STUDENT_ANSWER,
    // Grades
    GRADE_MARK
}