package com.example.cab302week4.model;

import java.time.LocalDate;
import java.util.Date;

public class AddSubject {
    private String subjectName;
    private LocalDate endDate;
    private LocalDate lastQuizDate;

    public AddSubject(String subjectName, LocalDate endDate) {
        this.subjectName = subjectName;
        this.endDate = endDate;
    }

    // For testing purposes
    @Override
    public String toString() {
        return "AddSubject {subjectName='" + subjectName + "', endDate=" + endDate + "}";
    }

//    // Getters and setters
//    public String getCharacter() {
//        return character;
//    }

    public String getCharacterName() {
        return subjectName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}

