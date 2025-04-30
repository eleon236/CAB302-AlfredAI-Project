package com.example.alfredAI.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MockAlfredDAO implements IAlfredDAO {

    public MockAlfredDAO() {

    }

    @Override
    public void addUser(String username, String password) {

    }

    @Override
    public int getUserID(String username, String password) {
        return 0;
    }

    @Override
    public int addQuest(String character, String name, Date endDate) {
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
    public String getQuestName(int questID) {
        return "";
    }

    @Override
    public LocalDate getQuestLastQuizDate(int questID) {
        return LocalDate.now();
    }

    @Override
    public String getQuestLastQuizScore(int questID) {
        return "";
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {

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
    public List<Quest> getUserQuests() {
        return List.of();
    }
}
