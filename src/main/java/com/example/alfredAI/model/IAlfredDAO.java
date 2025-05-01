package com.example.alfredAI.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */

public interface IAlfredDAO {

    ///////////////// Users /////////////////
    /**
     * Adds a new user to the database
     * @param username The user's username
     * @param password The user's password
     */
    public User addUser(String username, String password);

    /**
     * Retrieves a user's ID from the database
     * @param username The user's username
     * @param password The user's password
     */
    public int getUserID(String username, String password);

    ///////////////// Quests /////////////////
    /**
     * Adds a new quest to the database
     * @param character The quest's character name
     * @param name The quest's name
     * @param endDate The quest's end date
     */
    public int addQuest(
            String character,
            String name,
            Date endDate
    );

    /**
     * Updates an existing quest's distance travelled in the database
     * @param ID The quest's ID
     * @param distanceTravelled The quest's new distance travelled
     */
    public void updateQuestDistance(
            int ID,
            int distanceTravelled
    );

    /**
     * Updates an existing quest's last quiz data in the database
     * @param ID The quest's ID
     * @param lastQuizScore The quest's new last quiz score
     * @param lastQuizDate The quest's new last quiz date
     */
    public void updateQuestLastQuizData(
            int ID,
            String lastQuizScore,
            LocalDate lastQuizDate
    );

    /**
     * Retrieves a quest's information from the database
     * @param questID The quest's ID
     * @return The full quest information
     */
    public Quest getQuest(int questID);

    ///////////////// Flashcards /////////////////
    /**
     * Adds a new flashcard to the database
     * @param flashcard The flashcard to add
     */
    public void addFlashcard(Flashcard flashcard);

    /**
     * Updates an existing flashcard in the database
     * @param flashcard The flashcard to update
     */
    public void updateFlashcard(Flashcard flashcard);

    /**
     * Deletes an existing flashcard from the database
     * @param flashcard The flashcard to delete
     */
    public void deleteFlashcard(Flashcard flashcard);

    /**
     * Retrieves a quest's flashcards from the database
     * @param questID The quest's ID
     */
    public List<Flashcard> getQuestFlashcards(int questID);

    /**
     * Retrieves a user's quests from the database
     */
    public List<Quest> getUserQuests(int userID);
}