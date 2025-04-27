package com.example.cab302week4.model;

import java.time.LocalDate;

public class AddSubject {
    private String subjectName;
    private LocalDate endDate;
    private LocalDate lastQuizDate;

    public AddSubject(String subjectName, LocalDate endDate, LocalDate lastQuizDate) {
        this.subjectName = subjectName;
        this.endDate = endDate;
        this.lastQuizDate = lastQuizDate;
    }

    // For testing purposes
    @Override
    public String toString() {
        return "AddSubject {subjectName='" + subjectName + "', endDate=" + endDate + ", lastQuizDate=" + lastQuizDate + "}";
    }
}