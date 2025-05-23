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
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for the Flashcard view.
 * This class manages the display and interaction with flashcards for a specific quest,
 * including flipping cards, navigating through cards, and tracking mastery.
 */
public class FlashcardController {

    /**
     * Label to display the number of flashcards mastered out of the total.
     */
    @FXML private Label flashcardsMasteredLabel;
    /**
     * ProgressBar to visually represent the progress of mastering flashcards.
     */
    @FXML private ProgressBar progressBar;
    /**
     * Label to display the current flashcard's question or answer.
     */
    @FXML private Label cardLabel;
    /**
     * Label to indicate whether the current flashcard has been mastered.
     */
    @FXML private Label masteredLabel;

    /**
     * A list to hold the flashcards for the current quest, loaded from the database.
     */
    private List<Flashcard> flashcards = new ArrayList<>();  // Now real flashcards from DB
    /**
     * The index of the currently displayed flashcard in the `flashcards` list.
     */
    private int currentIndex = 0;
    /**
     * A boolean flag indicating whether the question (true) or answer (false) of the flashcard is currently showing.
     */
    private boolean showingQuestion = true;

    /**
     * Data Access Object for interacting with the SQLite database.
     * Used to retrieve flashcards and update their mastery status.
     */
    private SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();  // Access the database

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader.
     * It loads flashcards from the database for the current quest and initializes the
     * display, including the current card, mastery count, and progress bar.
     */
    @FXML
    public void initialize() {
        // Load flashcards from the database
        flashcards = alfredDAO.getQuestFlashcards(AlfredWelcome.currentQuestID);
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
            flashcardsMasteredLabel.setText("");
            progressBar.setVisible(false);
        } else {
            updateCard();

            // Set up mastered text and progress bar
            int numMastered = alfredDAO.getQuestFlashcardsMastered(AlfredWelcome.currentQuestID);
            flashcardsMasteredLabel.setText(numMastered + " of " + flashcards.size() + " flashcards mastered");
            progressBar.setProgress((double) numMastered / flashcards.size());
        }
    }

    /**
     * Updates the display of the current flashcard (question or answer) and its mastery status.
     * This method is called after navigation or flipping the card.
     */
    private void updateCard() {
        if (flashcards.isEmpty()) {
            cardLabel.setText("No flashcards available.");
            return;
        }
        Flashcard card = flashcards.get(currentIndex);
        cardLabel.setText(showingQuestion ? card.getQuestion() : card.getAnswer());
        masteredLabel.setText(card.getMastered() ? "✔ Mastered" : "");
    }

    /**
     * Handles the action when the "Flip" button is clicked.
     * Toggles between showing the question and the answer of the current flashcard.
     */
    @FXML
    private void onFlip() {
        showingQuestion = !showingQuestion;
        updateCard();
    }

    /**
     * Handles the action when the "Next" button is clicked.
     * Advances to the next flashcard in the list, looping back to the beginning if at the end.
     */
    @FXML
    private void onNext() {
        if (!flashcards.isEmpty()) {
            currentIndex = (currentIndex + 1) % flashcards.size();
            showingQuestion = true;
            updateCard();
        }
    }

    /**
     * Handles the action when the "Previous" button is clicked.
     * Moves to the previous flashcard in the list, looping to the end if at the beginning.
     */
    @FXML
    private void onPrevious() {
        if (!flashcards.isEmpty()) {
            currentIndex = (currentIndex - 1 + flashcards.size()) % flashcards.size();
        }
        showingQuestion = true;
        updateCard();
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * Navigates the user back to the Quest Page view.
     * @throws IOException If the FXML file for the quest page view cannot be loaded.
     */
    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) cardLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Handles the action when the "Edit Flashcards" button is clicked.
     * Navigates the user to the Edit Flashcards view.
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If the FXML file for the edit flashcards view cannot be loaded.
     */
    @FXML
    private void onEditFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("edit-flashcards-view.fxml"));
        Parent editRoot = loader.load();
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(editRoot);
    }


}
