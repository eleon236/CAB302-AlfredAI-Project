package com.example.cab302week4.controller;

import com.example.cab302week4.model.Flashcard;
import com.example.cab302week4.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
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

public class EditFlashcardsController {

    @FXML
    private ListView<Flashcard> flashcardList; // Now ListView<Flashcard> instead of String

    private SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO(); // connect to database

    @FXML
    public void initialize() {
        // Load real flashcards from the database
        List<Flashcard> flashcards = alfredDAO.getQuestFlashcards(1); // assuming questID = 1

        flashcardList.getItems().addAll(flashcards);

        flashcardList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Flashcard item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Show the question + answer
                    Text text = new Text("Q: " + item.getQuestion() + "\nA: " + item.getAnswer());

                    // Create edit and delete buttons
                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    // Create an HBox for the text
                    HBox hBox = new HBox(10, text);

                    // Create an HBox for the buttons
                    HBox buttonBox = new HBox(10, editButton, deleteButton);
                    buttonBox.setStyle("-fx-alignment: center-right;");

                    // Create a container VBox with the text and the buttons
                    VBox container = new VBox(hBox, buttonBox);

                    setGraphic(container);

                    // TODO: Add actual Edit/Delete button functionality here
                }
            }
        });
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302week4/flashcard-view.fxml"));
        Parent root = loader.load();

        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
}
