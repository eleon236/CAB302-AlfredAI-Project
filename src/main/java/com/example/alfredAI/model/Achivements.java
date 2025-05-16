package com.example.alfredAI.model;

import com.example.alfredAI.AlfredWelcome;

import java.sql.ResultSet;

public class Achivements {

    private final IAlfredDAO alfredDAO;

    public Achivements() {
        this.alfredDAO = new SqliteAlfredDAO();
    }

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