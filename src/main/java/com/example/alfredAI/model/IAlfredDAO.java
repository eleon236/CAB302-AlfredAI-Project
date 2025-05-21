package com.example.alfredAI.model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Interface for the Alfred Data Access Object that handles
 * the CRUD operations for the application's classes with the database.
 */
public interface IAlfredDAO {

    ///////////////// Users /////////////////
    /**
     * Adds a new user to the database
     * @param username The user's username
     * @param password The user's password
     * @return The new user object
     */
    User addUser(String username, String password);

    /**
     * Retrieves a user's ID from the database
     * @param username The user's username
     * @param password The user's password
     * @return The user's ID
     */
    int getUserID(String username, String password);

    ///////////////// Quests /////////////////
    /**
     * Adds a new quest to the database
     * @param userID The current user's ID
     * @param character The quest's character name
     * @param name The quest's name
     * @param endDate The quest's end date
     * @return The added quest's ID
     */
    int addQuest(
            int userID,
            String character,
            String name,
            Date endDate
    );

    /**
     * Updates an existing quest's distance travelled in the database
     * @param ID The quest's ID
     * @param distanceTravelled The quest's new distance travelled
     */
    void updateQuestDistance(int ID, int distanceTravelled);

    /**
     * Updates an existing quest's last quiz data in the database
     * @param ID The quest's ID
     * @param lastQuizScore The quest's new last quiz score
     * @param lastQuizDate The quest's new last quiz date
     */
    void updateQuestLastQuizData(
            int ID,
            String lastQuizScore,
            LocalDate lastQuizDate
    );

    /**
     * Updates an existing quest's current streak in the database
     * @param ID The quest's ID
     * @param newStreak The quest's new streak in days
     */
    void updateQuestStreak(int ID, int newStreak);

    /**
     * Retrieves a quest's information from the database
     * @param questID The quest's ID
     * @return The full quest information
     */
    Quest getQuest(int questID);

    ///////////////// Flashcards /////////////////
    /**
     * Adds a new flashcard to the database
     * @param questID The current selected quest's ID
     * @param flashcard The flashcard to add
     */
    void addFlashcard(int questID, Flashcard flashcard);

    /**
     * Updates an existing flashcard in the database
     * @param flashcard The flashcard to update
     */
    void updateFlashcard(Flashcard flashcard);

    /**
     * Deletes an existing flashcard from the database
     * @param flashcard The flashcard to delete
     */
    void deleteFlashcard(Flashcard flashcard);

    /**
     * Retrieves a quest's flashcards from the database
     * @param questID The quest's ID
     * @return The list of flashcards
     */
    List<Flashcard> getQuestFlashcards(int questID);

    /**
     * Retrieves the number of mastered flashcards in a quest from the database
     * @param questID The quest's ID
     * @return The number of mastered flashcards
     */
    int getQuestFlashcardsMastered(int questID);

    /**
     * Retrieves a user's quests from the database
     * @param userID The user's ID
     * @return The list of the user's quests
     */
    List<Quest> getUserQuests(int userID);

    ///////////////// Achievements /////////////////
    /**
     * Adds a new user to the achievements table in the database
     * @param userID The new user's ID
     */
    void addAchievement(int userID);

    /**
     * Updates an existing user's day streak achievements in the database
     * @param userID The user's ID
     * @param daysLoggedIn The user's updated days logged in
     * @param lastDayLoggedIn The user's updated last day logged in
     */
    void updateAchievementDays(int userID, int daysLoggedIn, long lastDayLoggedIn);

    /**
     * Updates an existing user's quiz completed achievement in the database
     * @param userID The user's ID
     * @param QuizCompleted The user's new quiz completed number
     */
    void updateQuizCompleted(int userID, int QuizCompleted);

    /**
     * Updates an existing user's current achievements in the database
     * @param userID The user's ID
     * @param daysLoggedIn The user's day logged in number
     * @param QuizCompleted The user's quiz completed number
     * @param otherVariables Other variables associated with the user
     */
    void updateAchievement(int userID, int daysLoggedIn, int QuizCompleted, String otherVariables);

    /**
     * Retrieves a user's achievements from the database
     * @param userID The user's ID
     * @return All the user's achievements
     */
    ResultSet getAchievement(int userID);
}