package com.example.alfredAI.model;

import com.example.alfredAI.AlfredWelcome;

import java.sql.ResultSet;

public class Achivements {

    private final IAlfredDAO alfredDAO;

    /**
     * Constructor for Achivements class.
     * Initializes the alfredDAO to interact with the database.
     */
    public Achivements() {
        this.alfredDAO = new SqliteAlfredDAO();
    }

    /**
     * Ensures that the current user is present in the achievements table.
     * If not, adds the user to the table.
     */
    public void ensureUserInAchievements() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                System.out.println("USER ALREADY IN TABLE");
            } else {
                alfredDAO.addAchievement(currentUserID);
                System.out.println("USER ADDED TO ACHIEVEMENTS TABLE");
            }
        } catch (Exception e) {
            System.err.println("Error ensuring user in achievements: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a day to the user's logged-in days if the current time is greater than the last logged-in time.
     */
    public void addUserDays() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                int days = resultSet.getInt("daysLoggedIn");
                long lastDayLoggedIn = resultSet.getLong("lastDayLoggedIn");
                long currentTime = System.currentTimeMillis();

                if (currentTime > lastDayLoggedIn) {
                    alfredDAO.updateAchievementDays(currentUserID, days + 1, currentTime);
                    System.out.println("USER DAYS UPDATED");
                } else {
                    System.out.println("No update needed for days logged in.");
                }
            } else {
                System.out.println("USER NOT FOUND IN ACHIEVEMENTS TABLE");
            }
        } catch (Exception e) {
            System.err.println("Error updating user days: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the number of quizzes completed by the user.
     */
    public void updateQuizCompleted() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                int quizCompleted = resultSet.getInt("QuizCompleted");
                alfredDAO.updateQuizCompleted(currentUserID, quizCompleted + 1);
                System.out.println("USER QUIZZES UPDATED");
            } else {
                System.out.println("USER NOT FOUND IN ACHIEVEMENTS TABLE");
            }
        } catch (Exception e) {
            System.err.println("Error updating quizzes completed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getDaysLoggedIn() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt("daysLoggedIn");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving days logged in: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves the number of quizzes completed by the user.
     * @return The number of quizzes completed.
     */
    public int getQuizzesCompleted() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt("QuizCompleted");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving quizzes completed: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}