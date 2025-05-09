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
        int currentUserID = AlfredWelcome.currentUserID; // Assuming this holds the current user's ID
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
}