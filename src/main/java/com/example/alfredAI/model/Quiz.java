package com.example.alfredAI.model;

import com.example.alfredAI.AlfredWelcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a daily quiz, with a list of the quiz's questions,
 * the quiz's result and its villain's ID
 */
public class Quiz {
    private final QuizQuestion[] questions;
    private int result;
    public int VillainID;

    /**
     * Constructor to make a Quiz based on provided flashcards and the days left in the quest
     * @param flashcards All the user's flashcards for the selected quest
     * @param questDaysLeft An int with the number of days left in the quest
     */
    public Quiz(List<Flashcard> flashcards, int questDaysLeft) {
        // Prepare questions array
        int quizLength = calcNumberOfQuestions(flashcards, questDaysLeft);
        QuizQuestion[] questions = new QuizQuestion[quizLength];

        // Define some values
        int questionNum = 0;
        boolean questionsFull = false;
        ArrayList<Flashcard> masteredCards = new ArrayList<>();

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
        this.result = 0;
    }

    /**
     * Getter for all quiz questions
     * @return All quiz questions
     */
    public QuizQuestion[] getQuestions() {
        return questions;
    }

    /**
     * Getter for the quiz result
     * @return Quiz result
     */
    public int getResult() {
        return result;
    }

    /**
     * Setter for the villain ID
     * @param villainNumber The new villain ID
     */
    public void setVillainID(int villainNumber){
        this.VillainID = villainNumber;
    }

    /**
     * Getter for the villain ID
     * @return The villain ID
     */
    public int getVillainID(){
        return VillainID;
    }

    /**
     * Calculates the number of questions for a daily quiz
     * @param flashcards All the user's flashcards for the selected quest
     * @param questDaysLeft An int with the number of days left in the quest
     * @return The number of questions
     */
    private int calcNumberOfQuestions(List<Flashcard> flashcards, int questDaysLeft) {
        // Find number of flashcards not mastered yet
        int numNotMastered = 0;
        for (Flashcard flashcard : flashcards) {
            if (!flashcard.getMastered()) {
                numNotMastered++;
            }
        }

        int numQuestions;

        // No flashcards
        if (flashcards.isEmpty()) {
            return 0;

        // All flashcards mastered
        } else if (numNotMastered == 0) {
            numQuestions = flashcards.size() / 2;

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
            numQuestions = 5;
        }

        if (numQuestions > flashcards.size()) {
            numQuestions = flashcards.size();
        }

        // Enforce maximum 30
        if (numQuestions > 30) {
            numQuestions = 30;
        }

        return numQuestions;
    }

    /**
     * Updates the stored userAnswer for a quiz question
     * @param questionNum The question number as displayed on the GUI
     * @param userAnswer The answer the user entered
     */
    public void enterUserAnswer(int questionNum, String userAnswer) {
        questions[questionNum - 1].setUserAnswer(userAnswer);
    }

    /**
     * Calculates the user's current progress in the quiz
     * @return A double between 0 and 1 representing the fraction of questions the user has completed
     */
    public double calcQuizProgress() {
        int questionsDone = 0;
        for (QuizQuestion question : questions) {
            // userAnswer != ""
            if (!Objects.equals(question.getUserAnswer(), "")) {
                questionsDone++;
            }
        }

        return (double) questionsDone / questions.length;
    }

    /**
     * Calculates and updates the user's quiz result
     */
    public void calcQuizResult() {
        int score = 0;
        for (QuizQuestion question : questions) {
            // userAnswer == correctAnswer
            if (Objects.equals(question.getUserAnswer().toLowerCase(), question.getCorrectAnswer().toLowerCase())) {
                score++;
            }
        }

        result = score;
    }

    /**
     * Updates mastered flashcards after a quiz in the database
     */
    public void updateFlashcardsMastered() {
        IAlfredDAO alfredDAO = new SqliteAlfredDAO();

        List<Flashcard> flashcards = alfredDAO.getQuestFlashcards(AlfredWelcome.currentQuestID);
        List<String> masteredQuestions = new ArrayList<>();

        // Find all questions the user got correct
        for (QuizQuestion question : questions) {
            // if userAnswer == correctAnswer
            if (Objects.equals(question.getUserAnswer().toLowerCase(), question.getCorrectAnswer().toLowerCase())) {
                masteredQuestions.add(question.getQuestion());
            }
        }

        // Update corresponding flashcards in the database
        for (Flashcard flashcard : flashcards) {
            for (String masteredQuestion : masteredQuestions) {
                if (Objects.equals(flashcard.getQuestion(), masteredQuestion)) {
                    alfredDAO.updateFlashcard(new Flashcard(
                            flashcard.getID(),
                            flashcard.getQuestion(),
                            flashcard.getAnswer(),
                            true
                    ));
                }
            }
        }
    }
}
