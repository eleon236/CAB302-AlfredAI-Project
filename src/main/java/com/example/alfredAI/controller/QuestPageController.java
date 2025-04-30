package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.IAlfredDAO;
import com.example.alfredAI.model.SqliteAlfredDAO;
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

//    private String questID;

//    public void setQuestID() {
//        this.questID = questID;
//        loadQuestDetails();
//    }

    public QuestPageController() {
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    public void initialize() {
        loadQuestDetails();
    }

    private void loadQuestDetails() {
        // Load quest details based on the questID
        String questName = alfredDAO.getQuestName(AlfredWelcome.currentQuestID);
        questDetailsLabel.setText("Quest Details for: " + questName);
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
        Stage stage = (Stage) Stage.getWindows().getFirst(); // Get the primary stage
        FXMLLoader loader;

        // Check if daily quiz has already been done today
        LocalDate lastDailyQuizDate = alfredDAO.getQuestLastQuizDate(AlfredWelcome.currentQuestID);
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