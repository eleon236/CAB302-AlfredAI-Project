package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.Flashcard;
import com.example.cab302week4.model.SqliteAlfredDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class flashcardController {

    @FXML private Label cardLabel;

    private List<Flashcard> flashcards = new ArrayList<>();  // Now real flashcards from DB
    private int currentIndex = 0;
    private boolean showingQuestion = true;

    private SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();  // Access the database

    @FXML
    public void initialize() {
        // Load flashcards from the database
        flashcards = alfredDAO.getQuestFlashcards(1); // assuming questID = 1 for now
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
        } else {
            updateCard();
        }
    }

    private void updateCard() {
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
            return;
        }
        Flashcard card = flashcards.get(currentIndex);
        cardLabel.setText(showingQuestion ? card.getQuestion() : card.getAnswer());
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
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onEditFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("edit-flashcards-view.fxml"));
        Parent editRoot = loader.load();
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(editRoot);
    }

}
