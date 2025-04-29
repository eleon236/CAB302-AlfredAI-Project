package com.example.cab302week4.controller;

import com.example.cab302week4.AlfredWelcome;
import com.example.cab302week4.model.IAlfredDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class QuestPageController {
    @FXML
    private Label questDetailsLabel;
    private IAlfredDAO alfredDAO;

    private String questID;

    public void setQuestID(String questID) {
        this.questID = questID;
        loadQuestDetails();
    }

    private void loadQuestDetails() {
        // Load quest details based on the questID
        questDetailsLabel.setText("Quest Details for: " + questID);
    }

    @FXML
    private void onGoToFlashcards() throws IOException {
//        Stage stage = (Stage) contactsListView.getScene().getWindow();
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("flashcard-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onGoToQuiz() throws IOException {
//        Stage stage = (Stage) contactsListView.getScene().getWindow();
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader;

        // Check if daily quiz has already been done today
        // TODO Implement actual questID
        LocalDate lastDailyQuizDate = alfredDAO.getQuestLastQuizDate(1);
        if (lastDailyQuizDate == null) {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-view.fxml"));
        } else if (lastDailyQuizDate.equals(LocalDate.now())) {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-results-view.fxml"));
        } else {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-view.fxml"));
        }

        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}