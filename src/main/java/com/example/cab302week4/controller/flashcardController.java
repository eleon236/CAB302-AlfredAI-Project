package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class flashcardController {
    @FXML private Label cardLabel;

    private static class Flashcard {
        final String question;
        final String answer;

        Flashcard(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    private final List<Flashcard> flashcards = List.of(
            new Flashcard("What is the capital of France?", "Paris"),
            new Flashcard("What is 2 + 2?", "4"),
            new Flashcard("What is the boiling point of water?", "100°C")
    );

    private int currentIndex = 0;
    private boolean showingQuestion = true;

    @FXML
    public void initialize() {
        updateCard();
    }

    private void updateCard() {
        Flashcard card = flashcards.get(currentIndex);
        cardLabel.setText(showingQuestion ? card.question : card.answer);
    }

    @FXML
    private void onFlip() {
        showingQuestion = !showingQuestion;
        updateCard();
    }

    @FXML
    private void onNext() {
        currentIndex = (currentIndex + 1) % flashcards.size();
        showingQuestion = true;
        updateCard();
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
