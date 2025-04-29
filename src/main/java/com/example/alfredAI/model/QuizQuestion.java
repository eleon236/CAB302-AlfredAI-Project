package com.example.alfredAI.model;

public class QuizQuestion {
    private String question;
    private String correctAnswer;
    private String userAnswer;

    public QuizQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
    }

    @Override
    public String toString() {
        return "QuizQuestion {question='" + question + "', correctAnswer='" + correctAnswer + "', userAnswer='" + userAnswer + "'}";
    }

    public String getQuestion() {
        return question;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
