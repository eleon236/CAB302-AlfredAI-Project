package com.example.cab302week4.model;

public class Quiz {
    private QuizQuestion[] questions;

    public Quiz(Flashcard[] flashcards, int quizLength) {
        QuizQuestion[] questions = new QuizQuestion[quizLength];

        // TODO: Implement random logic
        int questionNum = 0;
        for (Flashcard flashcard : flashcards) {

            if (!flashcard.getMastered()) {
                QuizQuestion question = new QuizQuestion(flashcard.getQuestion(), flashcard.getAnswer());
                questions[questionNum] = question;
                questionNum++;
            }

            if (questionNum == quizLength) {
                break;
            }

        }

        this.questions = questions;
    }

    public Quiz(QuizQuestion[] questions) {
        this.questions = questions;
    }

    public QuizQuestion[] getQuestions() {
        return questions;
    }

}
