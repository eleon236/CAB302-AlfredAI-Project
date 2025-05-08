package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class AddFlashcardController {

    @FXML private TextField questionField;
    @FXML private TextField answerField;
    @FXML private Label errorLabel;

    private final SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();

    @FXML
    private void onSubmit() throws Exception {
        String question = questionField.getText().trim();
        String answer = answerField.getText().trim();

        // Validate presence
        if (question.isEmpty() || answer.isEmpty()) {
            errorLabel.setText("Both question and answer must be filled.");
            return;
        }

        // Validate word count
        if (wordCount(question) > 20) {
            errorLabel.setText("Question must be 20 words or fewer.");
            return;
        }

        if (wordCount(answer) > 20) {
            errorLabel.setText("Answer must be 20 words or fewer.");
            return;
        }

        // Save and redirect
        Flashcard newCard = new Flashcard(AlfredWelcome.currentQuestID, question, answer, false);
        alfredDAO.addFlashcard(AlfredWelcome.currentQuestID, newCard);

        // Return to Quest Page
        Stage stage = (Stage) questionField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    private int wordCount(String input) {
        return input.isEmpty() ? 0 : input.trim().split("\\s+").length;
    }

    @FXML
    private void onCancel() throws Exception {
        Stage stage = (Stage) questionField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

}
