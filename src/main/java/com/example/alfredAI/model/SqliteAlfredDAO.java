package com.example.alfredAI.model;

import com.example.alfredAI.AlfredWelcome;

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
        createAchievementsTable();
        createUserQuestsTable();
        createQuestFlashcardsTable();

        // Uncomment this to reset quiz data so you can do the daily quiz again today
//        updateQuestLastQuizData(
//                AlfredWelcome.currentQuestID,
//                "0 / 5",
//                LocalDate.now().minusDays(1)
//        );
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
                    + "lastQuizScore VARCHAR,"
                    + "lastQuizDate DATE,"
                    + "highestQuizScore VARCHAR,"
                    + "currentStreakDays INT"
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
    public User addUser(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            // Return the new User with ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new User(generatedKeys.getInt(1), username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
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
    public int addQuest(int userID, String character, String name, Date endDate) {
        try {
            // Insert into quests
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quests (character, name, endDate, distanceTravelled) VALUES (?, ?, ?, ?)");
            statement.setString(1, character);
            statement.setString(2, name);
            statement.setDate(3, (java.sql.Date) endDate);
            statement.setInt(4, 0);
            statement.executeUpdate();

            // Get the id of the new quest
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int questID = 0;
            if (generatedKeys.next()) {
                questID = generatedKeys.getInt(1);
            }

            // Insert into userQuests
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO userQuests (userID, questID) VALUES (?, ?)");
            statement2.setInt(1, userID);
            statement2.setInt(2, questID);
            statement2.executeUpdate();

            // Return the questID
            return questID;

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
    public void updateQuestStreak(int ID, int newStreak) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE quests SET currentStreakDays = ? WHERE ID = ?");
            statement.setInt(1, newStreak);
            statement.setInt(2, ID);
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

                // Check date is not null before converting to LocalDate
                java.sql.Date sqlQuizDate = resultSet.getDate("lastQuizDate");
                LocalDate lastQuizDate = (sqlQuizDate != null) ? sqlQuizDate.toLocalDate() : null;

                String highestQuizScore = resultSet.getString("highestQuizScore");
                int currentStreakDays = resultSet.getInt("currentStreakDays");

                return new Quest(ID, character, name, endDate, distanceTravelled, lastQuizScore, lastQuizDate, highestQuizScore, currentStreakDays);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addFlashcard(int questID, Flashcard flashcard) {
        try {
            // Insert into flashcards
            PreparedStatement statement = connection.prepareStatement("INSERT INTO flashcards (question, answer, mastered) VALUES (?, ?, ?)");
            statement.setString(1, flashcard.getQuestion());
            statement.setString(2, flashcard.getAnswer());
            statement.setBoolean(3, flashcard.getMastered());
            statement.executeUpdate();

            // Get the id of the new flashcard
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int flashcardID = 0;
            if (generatedKeys.next()) {
                flashcardID = generatedKeys.getInt(1);
                flashcard.setID(flashcardID);
            }

            // Insert into questFlashcards
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO questFlashcards (questID, flashcardID) VALUES (?, ?)");
            statement2.setInt(1, questID);
            statement2.setInt(2, flashcardID);
            statement2.executeUpdate();

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
    public int getQuestFlashcardsMastered(int questID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM flashcards "
                            + "JOIN questFlashcards "
                            + "ON flashcards.ID = questFlashcards.flashcardID "
                            + "WHERE questFlashcards.questID = ? "
                            + "AND flashcards.mastered = 1");
            statement.setInt(1, questID);
            ResultSet resultSet = statement.executeQuery();
            // Return number of flashcards mastered
            while (resultSet.next()) {
                return resultSet.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Quest> getUserQuests(int userID) {
        List<Quest> quests = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT quests.ID, quests.name, quests.endDate FROM quests "
                            + "JOIN userQuests "
                            + "ON quests.ID = userQuests.questID "
                            + "WHERE userQuests.userID = ?");
            statement.setInt(1, userID);
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


    private void createAchievementsTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS achievements ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "userID INTEGER NOT NULL,"
                    + "daysLoggedIn INTEGER DEFAULT 0,"
                    + "lastDayLoggedIn DATE,"
                    + "QuizCompleted INTEGER DEFAULT 0,"
                    + "otherVariables TEXT,"
                    + "FOREIGN KEY (userID) REFERENCES users(ID)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addAchievement(int userID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO achievements (userID, daysLoggedIn, lastDayLoggedIn, QuizCompleted, otherVariables) VALUES (?, 0, 0, NULL, NULL)"
            );
            statement.setInt(1, userID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAchievement(int userID, int daysLoggedIn, int QuizCompleted, String otherVariables) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE achievements SET daysLoggedIn = ?, QuizCompleted = ?, otherVariables = ? WHERE userID = ?"
            );
            statement.setInt(1, daysLoggedIn);
            statement.setInt(2, QuizCompleted);
            statement.setString(3, otherVariables);
            statement.setInt(4, userID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAchievementDays(int userID, int daysLoggedIn, long lastDayLoggedIn) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE achievements SET daysLoggedIn = ?, lastDayLoggedIn = ? WHERE userID = ?"
            );
            statement.setInt(1, daysLoggedIn);
            statement.setLong(2, lastDayLoggedIn);
            statement.setInt(3, userID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuizCompleted(int userID, int QuizCompleted) {
        try {
            PreparedStatement statement = connection.prepareStatement(
        "UPDATE achievements SET QuizCompleted = ? WHERE userID = ?"
            );
            statement.setLong(1, QuizCompleted);
            statement.setInt(2, userID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ResultSet getAchievement(int userID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM achievements WHERE userID = ?"
            );
            statement.setInt(1, userID);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}