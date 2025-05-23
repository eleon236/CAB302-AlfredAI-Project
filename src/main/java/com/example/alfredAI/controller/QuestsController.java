package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ImageView bearImageView;

    private AchivementsController achivementsController;

    public QuestsController() {
        achivementsController = new AchivementsController();
    }

    /**
     * Initializes the controller class. This method is called after the FXML file has been loaded.
     * It sets up the image and loads quests into the ListView.
     */
    @FXML
    public void initialize() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/alfred.png")));
        bearImageView.setImage(image);

        loadQuestsIntoListView();
        achivementsController.addUserDays();
        achivementsController.ensureUserInAchievements(); // Call the method using the instance
    }

    /**
     * Handles the event when the "Add Quest" button is clicked.
     * It retrieves the subject name and end date from the input fields,
     * creates a new Quest object, and adds it to the database.
     */
    @FXML
    private void onlogoutButton() throws IOException {
        AlfredWelcome.currentUserID = 0;
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onGoToAddSubject() throws IOException {
//        Stage stage = (Stage) contactsListView.getScene().getWindow();
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("add-quest-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    private void loadQuestsIntoListView() {
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO(); // Create an instance
        List<Quest> quests = alfredDAO.getUserQuests(AlfredWelcome.currentUserID); // Call the no-argument method
        questsListView.getItems().addAll(quests);

        questsListView.setCellFactory(listView -> new ListCell<Quest>() { // Explicitly specify the type
            @Override
            protected void updateItem(Quest quest, boolean empty) { // Ensure method signature matches
                super.updateItem(quest, empty);
                if (empty || quest == null) {
                    setText(null);
                } else {
                    setText(quest.getSubjectName() + " - " + quest.getEndDate());
                }
            }
        });
    }

    @FXML
    private void onQuestSelected() throws IOException {
        Quest selectedQuest = (Quest) questsListView.getSelectionModel().getSelectedItem();
        if (selectedQuest == null) {
            return; // No item selected
        }

        // Temporarily store the selected questID
        AlfredWelcome.currentQuestID = selectedQuest.getId();

        // Navigate to the Quest page
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);

        // Pass the selected quest's ID to the new controller
//        QuestPageController questPageController = loader.getController();
//        questPageController.setQuestID(String.valueOf(selectedQuest.getId())); // Convert int to String

        Stage stage = (Stage) questsListView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void onGoToAchievements() throws IOException {
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("achivements.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }


}
