package com.example.alfredAI.model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MockAlfredDAO implements IAlfredDAO {

    public MockAlfredDAO() {

    }

    @Override
    public User addUser(String username, String password) {
        return new User();
    }

    @Override
    public int getUserID(String username, String password) {
        return 0;
    }

    @Override
    public int addQuest(int userID, String character, String name, Date endDate) {
        return 0;
    }

    @Override
    public void updateQuestDistance(int ID, int distanceTravelled) {

    }

    @Override
    public void updateQuestLastQuizData(int ID, String lastQuizScore, LocalDate lastQuizDate) {

    }

    @Override
    public Quest getQuest(int questID) {
        return null;
    }

    @Override
    public void addFlashcard(int questID, Flashcard flashcard) {

    }

    @Override
    public void updateFlashcard(Flashcard flashcard) {

    }

    @Override
    public void deleteFlashcard(Flashcard flashcard) {

    }

    @Override
    public List<Flashcard> getQuestFlashcards(int questID) {
        return List.of(new Flashcard[0]);
    }

    @Override
    public int getQuestFlashcardsMastered(int questID) {
        return 0;
    }

    @Override
    public List<Quest> getUserQuests(int userID) {
        return List.of();
    }


    @Override
    public void addAchievement(int userID) {
        // Mock implementation
    }

    @Override
    public void updateAchievement(int userID, int daysLoggedIn, int questsCompleted, String otherVariables) {
        // Mock implementation
    }

    @Override
    public void updateAchievementDays(int userID, int daysLoggedIn, long lastDayLoggedIn) {
        // Mock implementation
    }

    @Override
    public ResultSet getAchievement(int userID) {
        return null; // Mock implementation
    }

    @Override
    public void updateQuizCompleted(int userID, int QuizCompleted) {
        // Mock implementation
    }
}
