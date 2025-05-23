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

    /**
     *
     * @param id The unique identifier for the quest.
     * @param subjectName name of the subject
     * @param endDate last date of the quest
     */
    public Quest(int id, String subjectName, LocalDate endDate) { // Include ID in constructor
        this.id = id;
        this.subjectName = subjectName;
        this.endDate = endDate;
    }

/**
 * Constructs a Quest object with all properties.
 *
 * @param ID The unique identifier for the quest.
 * @param character The name of the character associated with the quest.
 * @param name The subject name of the quest.
 * @param endDate The date when the quest ends.
 * @param distanceTravelled The total distance travelled in the quest so far.
 * @param lastQuizScore The score achieved in the most recent quiz.
 * @param lastQuizDate The date the last quiz was taken.
 * @param highestQuizScore The highest score achieved in any quiz for this quest.
 * @param currentStreakDays The current number of consecutive days the user has completed quizzes.
 */
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

    public boolean pastQuestEndDate() {
        return (endDate.isBefore(LocalDate.now()));
    }

    // Increases the quest streak days by 1 after a daily quiz is completed
    public void updateQuestStreak() {
        if (pastQuestEndDate()) { return; }
        currentStreakDays++;
    }

    // Updates the distance travelled after a daily quiz is completed
    public void updateDistanceTravelled() {
        if (pastQuestEndDate()) { return; }

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

