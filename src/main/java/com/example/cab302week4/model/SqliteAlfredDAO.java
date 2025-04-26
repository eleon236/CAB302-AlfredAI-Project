package com.example.cab302week4.model;

import java.sql.*;
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

        insertSampleData();
    }

    private void insertSampleData() {
        try {
            // Clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM flashcards";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO flashcards (question, answer, mastered) VALUES "
                    + "('question 1?', 'answer 1', 0),"
                    + "('question 2?', 'answer 2', 1),"
                    + "('question 3?', 'answer 3', 1),"
                    + "('question 4?', 'answer 4', 0),"
                    + "('question 5?', 'answer 5', 0),"
                    + "('question 6?', 'answer 6', 0),"
                    + "('question 7?', 'answer 7', 0),"
                    + "('question 8?', 'answer 8', 0),"
                    + "('question 9?', 'answer 9', 0),"
                    + "('question 10?', 'answer 10', 0)";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
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