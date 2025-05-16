package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.IAlfredDAO;
import com.example.alfredAI.model.Quest;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class QuestPageController {
    @FXML
    private Label questNameLabel;
    @FXML
    private ImageView questProgressImage;
    @FXML
    private ImageView questCharacter;
    @FXML
    private ImageView questVillain;
    @FXML
    private Label distanceTravelledLabel;

    private IAlfredDAO alfredDAO;
    private Quest quest;

// test
    public QuestPageController() {
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    public void initialize() {
        loadQuestDetails();


        // Display correct quest progress image
        Image image;
        int distanceTravelled = quest.getDistanceTravelled();
        if (distanceTravelled < 25) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-1.png")));
        } else if (distanceTravelled < 75) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-2.png")));
        } else if (distanceTravelled < 150) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-3.png")));
        } else if (distanceTravelled < 250) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-4.png")));
        } else if (distanceTravelled < 400) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-5.png")));
        } else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/quest-progress-6.png")));
        }
        questProgressImage.setImage(image);

        // Update distance travelled label
        if (quest.pastQuestEndDate()) {
            distanceTravelledLabel.setText("This quest is finished. You travelled " + distanceTravelled + "km.");
        } else if (distanceTravelled == 0) {
            distanceTravelledLabel.setText("You haven't travelled any distance in this quest yet!");
        } else {
            distanceTravelledLabel.setText("You've travelled " + distanceTravelled + "km so far!");
        }

        String characterName = alfredDAO.getQuest(AlfredWelcome.currentQuestID).getCharacterName();
        // Set character images
        Image characterImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/"+characterName+".png")));
        questCharacter.setImage(characterImage);

        Image villainImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/villain.png")));
        questVillain.setImage(villainImage);
    }

    private void loadQuestDetails() {
        // Load quest details based on the questID
        quest = alfredDAO.getQuest(AlfredWelcome.currentQuestID);
        String questName = quest.getSubjectName();
        questNameLabel.setText(questName + " Quest");
    }

    @FXML
    private void onGoToFlashcards() throws IOException {
        Stage stage = (Stage) Stage.getWindows().get(0); // Get the primary stage
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("flashcard-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onGoToQuiz() throws IOException {
        Stage stage = (Stage) Stage.getWindows().getFirst(); // Get the primary stage
        FXMLLoader loader;

        // Check if daily quiz has already been done today
        LocalDate lastDailyQuizDate = alfredDAO.getQuest(AlfredWelcome.currentQuestID).getLastQuizDate();
        if (lastDailyQuizDate == null) {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-view.fxml"));
        } else if (lastDailyQuizDate.equals(LocalDate.now())) {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-results-view.fxml"));
        } else {
            loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-view.fxml"));
        }

        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) questNameLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onAddFlashcard() throws IOException {
        Stage stage = (Stage) questNameLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("add-flashcard-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onAddAIFlashcard() throws IOException {
        Stage stage = (Stage) questNameLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("add-ollama-flashcard-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }





}