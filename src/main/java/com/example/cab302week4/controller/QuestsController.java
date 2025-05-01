package com.example.cab302week4.controller;

import com.example.cab302week4.AlfredWelcome;
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
import java.util.Objects;

public class QuestsController {
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private DatePicker subjectEndDateTextField;

    @FXML
    private ListView questsListView;

    @FXML
    private Button logoutButton;

    @FXML
    private void onlogoutButton() throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private ImageView bearImageView;

    @FXML
    public void initialize() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Bear.png")));
        bearImageView.setImage(image);

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

    @FXML
    private void onQuestSelected() throws IOException {


        AddSubject selectedQuest = (AddSubject) questsListView.getSelectionModel().getSelectedItem();
        if (selectedQuest == null) {
            return; // No item selected
        }

        // Navigate to the Quest page
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("QuestPage.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);

        // Pass the selected quest's ID to the new controller
        QuestPageController questPageController = loader.getController();
        questPageController.setQuestID(String.valueOf(selectedQuest.getId())); // Convert int to String

        Stage stage = (Stage) questsListView.getScene().getWindow();
        stage.setScene(scene);
    }



//    @FXML
//    private void onAddSubject() {
//        String subjectName = subjectNameTextField.getText().trim();
//        LocalDate subjectEndDate = subjectEndDateTextField.getValue();
//
//        if (subjectName.isEmpty() || subjectEndDate == null) {
//            System.out.println("All fields must be filled out.");
//            return;
//        }
//
//        // Create an AddSubject object
//        AddSubject newSubject = new AddSubject(subjectName, subjectEndDate);
//
//        // Save the subject as a quest in the database
//        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();
//        alfredDAO.addQuest("Null", subjectName, java.sql.Date.valueOf(subjectEndDate));
//        // TODO: Add logic to save the subject to the database or list
//        System.out.println("Subject added: " + newSubject);
//
//        closeWindow();
//    }
//
//    private void closeWindow() {
//        Stage stage = (Stage) subjectNameTextField.getScene().getWindow();
//        stage.close();
//    }
}
