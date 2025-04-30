package com.example.alfredAI.model;

import java.time.LocalDate;

public class Quest {
    private int id; // Add ID field
    private String subjectName;
    private LocalDate endDate;

    public Quest(int id, String subjectName, LocalDate endDate) { // Include ID in constructor
        this.id = id;
        this.subjectName = subjectName;
        this.endDate = endDate;
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

