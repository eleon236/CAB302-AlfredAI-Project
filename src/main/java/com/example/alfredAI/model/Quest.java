package com.example.alfredAI.model;

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

    public Quest(int id, String subjectName, LocalDate endDate) { // Include ID in constructor
        this.id = id;
        this.subjectName = subjectName;
        this.endDate = endDate;
    }

    public Quest(int ID, String character, String name, LocalDate endDate, int distanceTravelled, String lastQuizScore, LocalDate lastQuizDate, String highestQuizScore) {
        this.id = ID;
        this.character = character;
        this.subjectName = name;
        this.endDate = endDate;
        this.distanceTravelled = distanceTravelled;
        this.lastQuizScore = lastQuizScore;
        this.lastQuizDate = lastQuizDate;
        this.highestQuizScore = highestQuizScore;
    }

    // For testing purposes
    @Override
    public String toString() {
        return "Quest {subjectName='" + subjectName + "', endDate=" + endDate + "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacterName() {
        return subjectName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}

