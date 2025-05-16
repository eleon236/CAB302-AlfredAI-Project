package com.example.alfredAI.controller;

import com.example.alfredAI.model.Achivements;

public class AchivementsController {

    private final Achivements achivements;

    public AchivementsController() {
        this.achivements = new Achivements();
    }

    public void ensureUserInAchievements() {
        achivements.ensureUserInAchievements();
    }

    public void addUserDays() {
        achivements.addUserDays();
    }

    public void updateQuizCompleted() {
        achivements.updateQuizCompleted();
    }
}