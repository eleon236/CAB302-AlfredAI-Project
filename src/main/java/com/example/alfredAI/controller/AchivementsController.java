package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.IAlfredDAO;
import com.example.alfredAI.model.SqliteAlfredDAO;

import java.sql.ResultSet;

public class AchivementsController {

    private IAlfredDAO alfredDAO;

    public AchivementsController() {
        alfredDAO = new SqliteAlfredDAO();
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
            e.printStackTrace();
        }
    }

    public void addUserDays() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                int days = resultSet.getInt("daysLoggedIn");
                int DaysSinceLastLogin = resultSet.getInt("lastDayLoggedIn");
//                Get Current unix timestamp, if current timestamp is greater than last login timestamp add 1 to days and update DaysSinceLastLogin
                // At the moment updates every time if its been a second since going to page for testing
                if (System.currentTimeMillis() / 1000 > DaysSinceLastLogin) {
                    alfredDAO.updateAchievementDays(currentUserID, days + 1, System.currentTimeMillis());
                } else {
                    alfredDAO.updateAchievementDays(currentUserID, days, DaysSinceLastLogin);
                }
                System.out.println("USER DAYS UPDATED");
            } else {
                System.out.println("USER NOT FOUND IN ACHIEVEMENTS TABLE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuizCompleted() {
        int currentUserID = AlfredWelcome.currentUserID;
        try {
            ResultSet resultSet = alfredDAO.getAchievement(currentUserID);
            if (resultSet != null && resultSet.next()) {
                int QuizCompleted = resultSet.getInt("QuizCompleted");
                alfredDAO.updateQuizCompleted(currentUserID, QuizCompleted + 1);
                System.out.println("USER QUIZZES UPDATED");
            } else {
                System.out.println("USER NOT FOUND IN ACHIEVEMENTS TABLE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}