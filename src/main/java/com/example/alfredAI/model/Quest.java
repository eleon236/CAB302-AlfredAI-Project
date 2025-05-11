package com.example.alfredAI.model;

import com.example.alfredAI.AlfredWelcome;

import java.time.LocalDate;

public class Quest {
    private int id; // Add ID field
    private String character;
    private String subjectName;
    private LocalDate endDate;
    private int distanceTravelled;
    private String lastQuizScore;
    private LocalDate lastQuizDate;
    private String highestQuizScore;
    private int currentStreakDays;

    public Quest(int id, String subjectName, LocalDate endDate) { // Include ID in constructor
        this.id = id;
        this.subjectName = subjectName;
        this.endDate = endDate;
    }

    public Quest(int ID, String character, String name, LocalDate endDate, int distanceTravelled, String lastQuizScore, LocalDate lastQuizDate, String highestQuizScore, int currentStreakDays) {
        this.id = ID;
        this.character = character;
        this.subjectName = name;
        this.endDate = endDate;
        this.distanceTravelled = distanceTravelled;
        this.lastQuizScore = lastQuizScore;
        this.lastQuizDate = lastQuizDate;
        this.highestQuizScore = highestQuizScore;
        this.currentStreakDays = currentStreakDays;
    }

    // For testing purposes
    @Override
    public String toString() {
        return "Quest {subjectName='" + subjectName + "', endDate=" + endDate + "}";
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacterName() {
        return character;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public String getLastQuizScore() {
        return lastQuizScore;
    }

    public LocalDate getLastQuizDate() {
        return lastQuizDate;
    }

    public String getHighestQuizScore() {
        return highestQuizScore;
    }

    public int getCurrentStreakDays() {
        return currentStreakDays;
    }

    // Increases the quest streak days by 1 after a daily quiz is completed
    public void updateQuestStreak() {
        currentStreakDays++;
    }

    // Updates the distance travelled after a daily quiz is completed
    public void updateDistanceTravelled() {

        // Add distance based on today's quiz score
        double scorePercent = (double) AlfredWelcome.quiz.getResult() / AlfredWelcome.quiz.getQuestions().length;
        int disToAdd = (int) ((scorePercent * 100) / 4); // Max you can travel in one day with no streak is 25km

        // Increase distance by length of current streak
        disToAdd = disToAdd + currentStreakDays;

        // Up to max of 50km in one day
        if (disToAdd > 50) {
            disToAdd = 50;
        }

        // Ensure min 1km
        if (disToAdd < 1) {
            disToAdd = 1;
        }

        distanceTravelled = disToAdd;

    }

}

