package com.example.cab302week4.model;

/**
 * A class representing a daily quiz, with a list of the quiz's questions
 */
public class Quiz {
    private QuizQuestion[] questions;

    /**
     * Constructor to make a Quiz based on provided flashcards and quizLength
     * @param flashcards All of the user's flashcards for the selected quest
     * @param questDaysLeft An int with the number of days left in the quest
     */
    public Quiz(Flashcard[] flashcards, int questDaysLeft) {
        int quizLength = calcNumberOfQuestions(flashcards, questDaysLeft);
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

    /**
     * Constructor to make a Quiz based on a provided list of questions
     * @param questions The quiz questions to be used
     */
    public Quiz(QuizQuestion[] questions) {
        this.questions = questions;
    }

    public QuizQuestion[] getQuestions() {
        return questions;
    }

    /**
     * Calculates the number of questions for a daily quiz
     * @param flashcards All of the user's flashcards for the selected quest
     * @param questDaysLeft An int with the number of days left in the quest
     * @return An int with the number of questions
     */
    private int calcNumberOfQuestions(Flashcard[] flashcards, int questDaysLeft) {
        // Find number of flashcards not mastered yet
        int numNotMastered = 0;
        for (Flashcard flashcard : flashcards) {
            if (!flashcard.getMastered()) {
                numNotMastered++;
            }
        }

        int numQuestions;

        // No flashcards
        if (flashcards.length == 0) {
            return 0;

        // All flashcards mastered
        } else if (numNotMastered == 0) {
            numQuestions = flashcards.length / 2;

        // It's the day the quest is ending
        } else if (questDaysLeft == 0) {
            numQuestions = numNotMastered;

        // Otherwise
        } else {
            // Add 1 so the user does just a bit more than the bare minimum
            numQuestions = (numNotMastered / questDaysLeft) + 1;
        }

        // Enforce minimum 5
        if (numQuestions < 5) {
            return 5;
        }

        // Enforce maximum 30
        if (numQuestions > 30) {
            return 30;
        }

        return numQuestions;

    }
}
