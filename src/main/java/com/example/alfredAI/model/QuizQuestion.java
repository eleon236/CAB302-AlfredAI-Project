package com.example.alfredAI.model;

/**
 * A class representing a single quiz question, with
 * the question, correct answer and user entered answer
 */
public class QuizQuestion {
    private String question;
    private String correctAnswer;
    private String userAnswer;

    /**
     * Constructor to make a QuizQuestion with a question and correct answer
     * @param question The question
     * @param correctAnswer The correct expected answer
     */
    public QuizQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
    }

    /**
     * toString method used for unit tests, to easily compare different object values
     * @return A string containing all fields in a QuizQuestion object
     */
    @Override
    public String toString() {
        return "QuizQuestion {question='" + question + "', correctAnswer='" + correctAnswer + "', userAnswer='" + userAnswer + "'}";
    }

    /**
     * Getter for the question
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Getter for the correct answer
     * @return The correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Getter for the user's answer
     * @return The user's answer
     */
    public String getUserAnswer() {
        return userAnswer;
    }

    /**
     * Setter for the user's answer
     * @param userAnswer The user's new answer
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
