package com.example.cab302week4.model;

import java.util.ArrayList;
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
    public void addQuest(String character, String name, Date endDate) {

    }

    @Override
    public void updateQuestDistance(int ID, int distanceTravelled) {

    }

    @Override
    public void updateQuestLastQuizData(int ID, int lastQuizScore, Date lastQuizDate) {

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
}
