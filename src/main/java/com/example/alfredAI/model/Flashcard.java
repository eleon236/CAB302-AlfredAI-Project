package com.example.alfredAI.model;

/**
 * Represents a single flashcard with a question, an answer, and a mastery status.
 * This class serves as a model for flashcard data, including its unique identifier.
 */
public class Flashcard {
    /**
     * The unique identifier for the flashcard.
     */
    private int ID;
    /**
     * The question displayed on the flashcard.
     */
    private String question;
    /**
     * The answer to the question on the flashcard.
     */
    private String answer;
    /**
     * A boolean flag indicating whether the flashcard has been mastered by the user.
     * `true` if mastered, `false` otherwise.
     */
    private boolean mastered;

    /**
     * Constructs a new Flashcard with a specified ID, question, answer, and mastery status.
     *
     * @param ID The unique identifier for the flashcard.
     * @param question The question string for the flashcard.
     * @param answer The answer string for the flashcard.
     * @param mastered The mastery status of the flashcard (true if mastered, false otherwise).
     */
    public Flashcard(int ID, String question, String answer, boolean mastered) {
        this.ID = ID;
        this.question = question;
        this.answer = answer;
        this.mastered = mastered;
    }

    /**
     * Constructs a new Flashcard with a question and an answer.
     * The ID is not set here and mastery status defaults to `false`.
     * This constructor is useful when creating a new flashcard before it's saved to a database
     * where an ID would be assigned.
     *
     * @param question The question string for the flashcard.
     * @param answer The answer string for the flashcard.
     */
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.mastered = false;
    }

    /**
     * Returns the unique ID of the flashcard.
     * @return The integer ID of the flashcard.
     */
    public int getID() {
        return ID;
    }
    /**
     * Returns the question of the flashcard.
     * @return The question string.
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Returns the answer of the flashcard.
     * @return The answer string.
     */
    public String getAnswer() {
        return answer;
    }
    /**
     * Returns the mastery status of the flashcard.
     * @return `true` if the flashcard is mastered, `false` otherwise.
     */
    public boolean getMastered() {
        return mastered;
    }

    /**
     * Sets the unique ID of the flashcard.
     * @param ID The new integer ID for the flashcard.
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Sets the question of the flashcard.
     * @param question The new question string for the flashcard.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * Sets the answer of the flashcard.
     * @param answer The new answer string for the flashcard.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    /**
     * Sets the mastery status of the flashcard.
     * @param mastered The new mastery status (`true` for mastered, `false` for not mastered).
     */
    public void setMastered(boolean mastered) {
        this.mastered = mastered;
    }

}
