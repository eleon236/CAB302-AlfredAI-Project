package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class QuestsController {
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private DatePicker subjectEndDateTextField;
    @FXML
    private ImageView bearImageView;
    @FXML
    private ListView questsListView;

    private IContactDAO contactDAO;
    private IAlfredDAO alfredDAO;
    @FXML
    private void initialize() {
        try {
//            Image bearImage = new Image(getClass().getResource("/img/Bear.png").toExternalForm());
//            bearImageView.setImage(bearImage);
        } catch (Exception e) {
            System.out.println("Bear image not found!");
        }

        loadQuestsIntoListView();
    }

    @FXML
    private void onGoToAddSubject() throws IOException {
//        Stage stage = (Stage) contactsListView.getScene().getWindow();
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-subject-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void loadQuestsIntoListView() {
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO(); // Create an instance
        List<AddSubject> quests = alfredDAO.getUserQuests(); // Call the no-argument method
        questsListView.getItems().addAll(quests);

        questsListView.setCellFactory(listView -> new ListCell<AddSubject>() { // Explicitly specify the type
            @Override
            protected void updateItem(AddSubject quest, boolean empty) { // Ensure method signature matches
                super.updateItem(quest, empty);
                if (empty || quest == null) {
                    setText(null);
                } else {
                    setText(quest.getCharacterName() + " - " + quest.getEndDate());
                }
            }
        });
    }

    private void handleButton(String subject) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Adventure");
        alert.setHeaderText(null);
        alert.setContentText("You selected: " + subject);
        alert.showAndWait();
    }

    @FXML
    private void onAddSubject() {
        String subjectName = subjectNameTextField.getText().trim();
        LocalDate subjectEndDate = subjectEndDateTextField.getValue();

        if (subjectName.isEmpty() || subjectEndDate == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        // Create an AddSubject object
        AddSubject newSubject = new AddSubject(subjectName, subjectEndDate);

        // Save the subject as a quest in the database
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();
        alfredDAO.addQuest("Null", subjectName, java.sql.Date.valueOf(subjectEndDate));
        // TODO: Add logic to save the subject to the database or list
        System.out.println("Subject added: " + newSubject);

        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) subjectNameTextField.getScene().getWindow();
        stage.close();
    }
}
