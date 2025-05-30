package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome; // Import AlfredWelcome
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for the Edit Flashcards view.
 * This class handles the display, editing, and deletion of flashcards
 * associated with the currently selected quest.
 */
public class EditFlashcardsController {

    /**
     * ListView to display the flashcards.
     * Each item in the list will be a Flashcard object.
     */
    @FXML
    private ListView<Flashcard> flashcardList;

    /**
     * Data Access Object for interacting with the SQLite database.
     * This is used to load, update, and delete flashcards.
     */
    private SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO(); // connect to database

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader.
     * It loads flashcards from the database for the current quest and sets up the custom
     * cell factory for the ListView to display flashcard details and action buttons.
     */
    @FXML
    public void initialize() {
        // Load real flashcards from the database for the current quest
        int currentQuestId = AlfredWelcome.currentQuestID;
        List<Flashcard> flashcards = alfredDAO.getQuestFlashcards(currentQuestId);

        flashcardList.getItems().addAll(flashcards);

        flashcardList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Flashcard item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Text text = new Text("Q: " + item.getQuestion() + "\nA: " + item.getAnswer());

                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    HBox hBox = new HBox(10, text);
                    HBox buttonBox = new HBox(10, editButton, deleteButton);
                    buttonBox.setStyle("-fx-alignment: center-right;");
                    VBox container = new VBox(hBox, buttonBox);

                    setGraphic(container);

                    // EDIT functionality
                    editButton.setOnAction(e -> {
                        TextInputDialog questionDialog = new TextInputDialog(item.getQuestion());
                        questionDialog.setTitle("Edit Flashcard");
                        questionDialog.setHeaderText("Edit Question");
                        questionDialog.setContentText("Question:");

                        Optional<String> newQuestion = questionDialog.showAndWait();

                        if (newQuestion.isPresent()) {
                            TextInputDialog answerDialog = new TextInputDialog(item.getAnswer());
                            answerDialog.setTitle("Edit Flashcard");
                            answerDialog.setHeaderText("Edit Answer");
                            answerDialog.setContentText("Answer:");

                            Optional<String> newAnswer = answerDialog.showAndWait();

                            if (newAnswer.isPresent()) {
                                // Update the flashcard object
                                item.setQuestion(newQuestion.get());
                                item.setAnswer(newAnswer.get());

                                // Update the database
                                alfredDAO.updateFlashcard(item);

                                // Refresh the ListView
                                flashcardList.refresh();
                            }
                        }
                    });

                    // DELETE functionality
                    deleteButton.setOnAction(e -> {
                        // Confirm deletion
                        TextInputDialog confirmDialog = new TextInputDialog();
                        confirmDialog.setTitle("Delete Flashcard");
                        confirmDialog.setHeaderText("Are you sure you want to delete this flashcard?");
                        confirmDialog.setContentText("Type 'YES' to confirm:");

                        Optional<String> confirmation = confirmDialog.showAndWait();

                        if (confirmation.isPresent() && confirmation.get().equalsIgnoreCase("YES")) {
                            // Remove from database
                            alfredDAO.deleteFlashcard(item);

                            // Remove from ListView
                            flashcardList.getItems().remove(item);
                        }
                    });

                }
            }
        });
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * This method loads the "flashcard-view.fxml" and sets it as the current scene,
     * effectively navigating back to the previous view.
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If the FXML file for the flashcard view cannot be loaded.
     */
    @FXML
    private void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/alfredAI/flashcard-view.fxml"));
        Parent root = loader.load();

        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
}