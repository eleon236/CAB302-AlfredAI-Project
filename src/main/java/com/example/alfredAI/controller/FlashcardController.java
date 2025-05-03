package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardController {

    @FXML private Label flashcardsMasteredLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Label cardLabel;
    @FXML private Label masteredLabel;

    private List<Flashcard> flashcards = new ArrayList<>();  // Now real flashcards from DB
    private int currentIndex = 0;
    private boolean showingQuestion = true;

    private SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();  // Access the database

    @FXML
    public void initialize() {
        // Load flashcards from the database
        flashcards = alfredDAO.getQuestFlashcards(AlfredWelcome.currentQuestID);
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
        } else {
            updateCard();
        }

        // Set up mastered text and progress bar
        int numMastered = alfredDAO.getQuestFlashcardsMastered(AlfredWelcome.currentQuestID);
        flashcardsMasteredLabel.setText(numMastered + " of " + flashcards.size() + " flashcards mastered");
        progressBar.setProgress((double) numMastered / flashcards.size());
    }

    private void updateCard() {
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
            return;
        }
        Flashcard card = flashcards.get(currentIndex);
        cardLabel.setText(showingQuestion ? card.getQuestion() : card.getAnswer());
        masteredLabel.setText(card.getMastered() ? "✔ Mastered" : "");
    }

    @FXML
    private void onFlip() {
        showingQuestion = !showingQuestion;
        updateCard();
    }

    @FXML
    private void onNext() {
        if (!flashcards.isEmpty()) {
            currentIndex = (currentIndex + 1) % flashcards.size();
            showingQuestion = true;
            updateCard();
        }
    }


    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) cardLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onEditFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("edit-flashcards-view.fxml"));
        Parent editRoot = loader.load();
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(editRoot);
    }

}
