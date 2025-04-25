package com.example.cab302week4.model;

import java.util.ArrayList;

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
        // Prepare questions array
        int quizLength = calcNumberOfQuestions(flashcards, questDaysLeft);
        QuizQuestion[] questions = new QuizQuestion[quizLength];

        // Define some values
        int questionNum = 0;
        boolean questionsFull = false;
        ArrayList<Flashcard> masteredCards = new ArrayList<Flashcard>();

        // Add questions that aren't mastered yet
        for (Flashcard flashcard : flashcards) {
            // If not mastered, add the question and increment questionNum
            if (!flashcard.getMastered()) {
                QuizQuestion question = new QuizQuestion(flashcard.getQuestion(), flashcard.getAnswer());
                questions[questionNum] = question;
                questionNum++;

                if (questionNum == quizLength) {
                    questionsFull = true;
                    break;
                }

            // If mastered, add to the mastered ArrayList
            } else {
                masteredCards.add(flashcard);
            }
        }

        // If questions isn't full, add random questions that are already mastered
        if (!questionsFull) {
            int questionsLeft = quizLength - questionNum;
            for (int i = 0; i < questionsLeft; i++) {
                // Select a random mastered question to add
                int randomNum = (int)(Math.random() * masteredCards.size()); // 0 to masteredCards.size() - 1
                QuizQuestion question = new QuizQuestion(masteredCards.get(randomNum).getQuestion(), masteredCards.get(randomNum).getAnswer());
                questions[questionNum] = question;
                questionNum++;

                // Remove the question so it isn't selected again
                masteredCards.remove(randomNum);

                // Check if questions is full
                if (questionNum == quizLength) {
                    break;
                }
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
