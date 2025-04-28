package com.example.cab302week4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class QuestPageController {
    @FXML
    private Label questDetailsLabel;

    private String questID;

    public void setQuestID(String questID) {
        this.questID = questID;
        loadQuestDetails();
    }

    private void loadQuestDetails() {
        // Load quest details based on the questID
        questDetailsLabel.setText("Quest Details for: " + questID);
    }
}