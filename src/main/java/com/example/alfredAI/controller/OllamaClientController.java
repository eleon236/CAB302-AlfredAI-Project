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

public class OllamaClientController {

    @FXML
    private TextField topicTextField;

    @FXML
    private ListView<String> flashcardListView;

    private ObservableList<String> flashcardItems = FXCollections.observableArrayList();

    private final OllamaService ollamaService = new OllamaService(); // Instantiate the service
    private final SqliteAlfredDAO dao = new SqliteAlfredDAO();

    @FXML
    public void initialize() {
        if (flashcardListView != null) {
            flashcardListView.setItems(flashcardItems);
        }
    }

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

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}