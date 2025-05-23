package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import com.example.alfredAI.model.OllamaService; // Import the service
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the Ollama Client view.
 * This class handles the logic for generating flashcards using the Ollama AI service
 * based on a user-provided topic, displaying them, and saving them to the database.
 */
public class OllamaClientController {

    /**
     * TextField where the user enters the topic for flashcard generation.
     */
    @FXML
    private TextField topicTextField;

    /**
     * ListView to display the generated flashcards (as strings).
     */
    @FXML
    private ListView<String> flashcardListView;

    /**
     * An ObservableList to hold the string representations of the generated flashcards,
     * which is bound to the `flashcardListView`.
     */
    private ObservableList<String> flashcardItems = FXCollections.observableArrayList();

    /**
     * An instance of `OllamaService` to communicate with the Ollama AI model.
     */
    private final OllamaService ollamaService = new OllamaService(); // Instantiate the service
    /**
     * An instance of `SqliteAlfredDAO` to interact with the SQLite database for saving flashcards.
     */
    private final SqliteAlfredDAO dao = new SqliteAlfredDAO();

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader.
     * It sets the `flashcardItems` as the source for the `flashcardListView`.
     */
    @FXML
    public void initialize() {
        if (flashcardListView != null) {
            flashcardListView.setItems(flashcardItems);
        }
    }

    /**
     * Handles the action when the "Generate" button is clicked.
     * It retrieves the topic from the `topicTextField`, calls the `OllamaService` to generate flashcards,
     * updates the `flashcardListView` with the generated cards, and saves them to the database
     * for the `currentQuestID`.
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void generateFromInput(ActionEvent event) {
        String topic = topicTextField.getText();
        if (topic != null && !topic.trim().isEmpty()) {
            try {
                List<Flashcard> generatedFlashcards = ollamaService.generateFlashcards(topic.trim());
                if (generatedFlashcards != null) {
                    flashcardItems.clear();
                    for (Flashcard flashcard : generatedFlashcards) {
                        flashcardItems.add("Q: " + flashcard.getQuestion() + "\nA: " + flashcard.getAnswer());
                        dao.addFlashcard(AlfredWelcome.currentQuestID, flashcard);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error generating flashcards: " + e.getMessage());
                e.printStackTrace();
                // Handle the error in the UI (e.g., show an alert)
            }
        } else {
            System.err.println("Please enter a topic.");
        }
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * Navigates the user back to the "quests-view.fxml" scene.
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If the FXML file for the quests view cannot be loaded.
     */
    @FXML
    private void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}