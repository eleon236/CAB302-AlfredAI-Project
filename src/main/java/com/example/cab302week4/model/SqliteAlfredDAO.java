package com.example.cab302week4.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqliteAlfredDAO implements IAlfredDAO {
    private Connection connection;

    public SqliteAlfredDAO() {
        connection = SqliteConnection.getInstance();
        createUsersTable();
        createQuestsTable();
        createFlashcardsTable();

        createUserQuestsTable();
        createQuestFlashcardsTable();
    }

    private void createUsersTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void createQuestsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quests ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "character VARCHAR NOT NULL,"
                    + "name VARCHAR NOT NULL,"
                    + "endDate DATE NOT NULL,"
                    + "distanceTravelled INTEGER NOT NULL,"
                    + "lastQuizScore INTEGER,"
                    + "lastQuizDate DATE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void createFlashcardsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS flashcards ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "question VARCHAR NOT NULL,"
                    + "answer VARCHAR NOT NULL,"
                    + "mastered BOOL NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void createUserQuestsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS userQuests ("
                    + "userID INTEGER,"
                    + "questID INTEGER,"
                    + "PRIMARY KEY (userID, questID),"
                    + "FOREIGN KEY (userID) REFERENCES users(ID),"
                    + "FOREIGN KEY (questID) REFERENCES quests(ID)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void createQuestFlashcardsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS questFlashcards ("
                    + "questID INTEGER,"
                    + "flashcardID INTEGER,"
                    + "PRIMARY KEY (questID, flashcardID),"
                    + "FOREIGN KEY (questID) REFERENCES quests(ID),"
                    + "FOREIGN KEY (flashcardID) REFERENCES flashcards(ID)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void addUser(String username, String password) {

    }

    @Override
    public int getUserID(String username, String password) {
        return 0;
    }

    @Override
    public void addQuest(String character, String name, Date endDate, int distanceTravelled, int lastQuizScore, Date lastQuizDate) {

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
    public Flashcard[] getUserQuests(int questID) {
        return new Flashcard[0];
    }
}