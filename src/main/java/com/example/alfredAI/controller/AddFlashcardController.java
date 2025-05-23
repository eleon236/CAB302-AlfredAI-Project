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

/**
 * Controller class for the Add Flashcard view.
 * Handles the logic for adding new flashcards, including input validation and saving to the database.
 */
public class AddFlashcardController {

    /**
     * TextField for entering the flashcard question.
     */
    @FXML private TextField questionField;
    /**
     * TextField for entering the flashcard answer.
     */
    @FXML private TextField answerField;
    /**
     * Label for displaying error messages to the user.
     */
    @FXML private Label errorLabel;

    /**
     * Data Access Object for interacting with the SQLite database.
     */
    private final SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();

    /**
     * Handles the submission of the new flashcard.
     * Validates the input fields (presence and word count), saves the flashcard to the database,
     * and redirects the user back to the Quest Page.
     * @throws Exception if an error occurs during FXML loading or scene transition.
     */
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

    /**
     * Calculates the number of words in a given string.
     * @param input The string to count words from.
     * @return The number of words in the input string. Returns 0 if the input is empty.
     */
    private int wordCount(String input) {
        return input.isEmpty() ? 0 : input.trim().split("\\s+").length;
    }

    /**
     * Handles the cancellation of adding a new flashcard.
     * Redirects the user back to the Quest Page without saving any changes.
     * @throws Exception if an error occurs during FXML loading or scene transition.
     */
    @FXML
    private void onCancel() throws Exception {
        Stage stage = (Stage) questionField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

}
