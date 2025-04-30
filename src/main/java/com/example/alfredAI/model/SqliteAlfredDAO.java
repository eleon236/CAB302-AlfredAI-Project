package com.example.alfredAI.model;

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
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            // TODO Update when there's a user class
            // Set the id of the new user
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                user.setId(generatedKeys.getInt(1));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getUserID(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT ID FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            // Return the ID
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addQuest(String character, String name, Date endDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quests (character, name, endDate, distanceTravelled) VALUES (?, ?, ?, ?)");
            statement.setString(1, character);
            statement.setString(2, name);
            statement.setDate(3, (java.sql.Date) endDate);
            statement.setInt(4, 0);
            statement.executeUpdate();
            // Return the id of the new quest
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateQuestDistance(int ID, int distanceTravelled) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE quests SET distanceTravelled = ? WHERE ID = ?");
            statement.setInt(1, distanceTravelled);
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateQuestLastQuizData(int ID, String lastQuizScore, LocalDate lastQuizDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE quests SET lastQuizScore = ?, lastQuizDate = ? WHERE ID = ?");
            statement.setString(1, lastQuizScore);
            statement.setDate(2, java.sql.Date.valueOf(lastQuizDate));
            statement.setInt(3, ID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Quest getQuest(int questID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quests WHERE ID = ?");
            statement.setInt(1, questID);
            // Return the last quiz date
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String character = resultSet.getString("character");
                String name = resultSet.getString("name");
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                int distanceTravelled = resultSet.getInt("distanceTravelled");
                String lastQuizScore = resultSet.getString("lastQuizScore");
                LocalDate lastQuizDate = resultSet.getDate("lastQuizDate").toLocalDate();
                String highestQuizScore = resultSet.getString("highestQuizScore");

                return new Quest(ID, character, name, endDate, distanceTravelled, lastQuizScore, lastQuizDate, highestQuizScore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getQuestName(int questID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM quests WHERE ID = ?");
            statement.setInt(1, questID);
            // Return the last quiz date
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LocalDate getQuestLastQuizDate(int questID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT lastQuizDate FROM quests WHERE ID = ?");
            statement.setInt(1, questID);
            // Return the last quiz date
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getDate("lastQuizDate") == null) {
                    return null;
                }
                return resultSet.getDate("lastQuizDate").toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getQuestLastQuizScore (int questID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT lastQuizScore FROM quests WHERE ID = ?");
            statement.setInt(1, questID);
            // Return the last quiz date
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("lastQuizScore");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO flashcards (question, answer, mastered) VALUES (?, ?, ?)");
            statement.setString(1, flashcard.getQuestion());
            statement.setString(2, flashcard.getAnswer());
            statement.setBoolean(3, flashcard.getMastered());
            statement.executeUpdate();
            // Set the id of the new flashcard
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                flashcard.setID(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFlashcard(Flashcard flashcard) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE flashcards SET question = ?, answer = ?, mastered = ? WHERE ID = ?"
            );
            statement.setString(1, flashcard.getQuestion());
            statement.setString(2, flashcard.getAnswer());
            statement.setBoolean(3, flashcard.getMastered());
            statement.setInt(4, flashcard.getID());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteFlashcard(Flashcard flashcard) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM flashcards WHERE ID = ?");
            statement.setInt(1, flashcard.getID());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Flashcard> getQuestFlashcards(int questID) {
        List<Flashcard> flashcards = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT flashcards.ID, flashcards.question, flashcards.answer, flashcards.mastered FROM flashcards "
                        + "JOIN questFlashcards "
                        + "ON flashcards.ID = questFlashcards.flashcardID "
                        + "WHERE questFlashcards.questID = ?");
            statement.setInt(1, questID);
            ResultSet resultSet = statement.executeQuery();
            // Return all flashcards selected
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                Boolean mastered = resultSet.getBoolean("mastered");
                Flashcard flashcard = new Flashcard(ID, question, answer, mastered);
                flashcards.add(flashcard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flashcards;
    }

    @Override
    public List<Quest> getUserQuests() {
        List<Quest> quests = new ArrayList<>();
        try {
            String query = "SELECT ID, name, endDate FROM quests"; // Include ID in the query
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID"); // Retrieve the ID
                String name = resultSet.getString("name");
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                quests.add(new Quest(id, name, endDate)); // Pass ID to the constructor
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quests;
    }

}