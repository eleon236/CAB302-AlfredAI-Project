package com.example.cab302week4.model;

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
}
