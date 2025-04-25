package com.example.cab302week4.model;

public class Flashcard {
    private int ID;
    private String question;
    private String answer;
    private boolean mastered;

    public Flashcard(int ID, String question, String answer, boolean mastered) {
        this.ID = ID;
        this.question = question;
        this.answer = answer;
        this.mastered = mastered;
    }

    public int getID() {
        return ID;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public boolean getMastered() {
        return mastered;
    }

}
