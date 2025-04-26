package com.example.cab302week4.controller;

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

public class EditFlashcardsController {

    @FXML
    private ListView<String> flashcardList;

    @FXML
    public void initialize() {
        // For now, populate dummy flashcards (later replace with real ones)
        flashcardList.getItems().addAll(
                "Q: What is Java? - A: A programming language",
                "Q: What is JavaFX? - A: A GUI framework"
        );

        flashcardList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Create the text for the flashcard (question + answer)
                    Text text = new Text(item);

                    // Create buttons for editing and deleting
                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    // Create an HBox for the text and buttons
                    HBox hBox = new HBox(10, text);

                    // Add buttons to the right side by creating another HBox for the buttons
                    HBox buttonBox = new HBox(10, editButton, deleteButton);
                    buttonBox.setSpacing(10);
                    buttonBox.setStyle("-fx-alignment: center-right;"); // Align buttons to the right

                    // Add both hBoxes to the main HBox (text + buttons)
                    VBox container = new VBox();
                    container.getChildren().addAll(hBox, buttonBox);

                    setGraphic(container);

                    // TODO: Hook up real edit/delete logic here
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
