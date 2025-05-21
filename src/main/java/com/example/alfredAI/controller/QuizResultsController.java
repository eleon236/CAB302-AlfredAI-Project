package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.IAlfredDAO;
import com.example.alfredAI.model.QuizQuestion;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A controller for the quiz results page functionalities
 */
public class QuizResultsController {
    @FXML
    private Label resultsLabel;
    @FXML
    private VBox questionsContainer;
    @FXML
    private ImageView questCharacter;
    @FXML
    private ImageView questVillan;

    private IAlfredDAO alfredDAO;

    /**
     * A constructor which initialises the connection to the database
     */
    public QuizResultsController() {
        alfredDAO = new SqliteAlfredDAO();
    }

    /**
     * Initialises the quiz results page by setting all images and question answers
     */
    @FXML
    public void initialize() {
        resultsLabel.setText(alfredDAO.getQuest(AlfredWelcome.currentQuestID).getLastQuizScore());

        if (AlfredWelcome.quiz == null) {
            questionsContainer.getChildren().add(new Label("Come back again tomorrow to do your next daily quiz!"));
            questionsContainer.setAlignment(Pos.CENTER);
            return;
        }

        String characterName = alfredDAO.getQuest(AlfredWelcome.currentQuestID).getCharacterName();
        // Set character images
        Image characterImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/"+characterName+".png")));
        questCharacter.setImage(characterImage);

        String villainNo = Integer.toString(AlfredWelcome.quiz.getVillainID());
        Image VillianImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/villians/villain"+villainNo+".png")));
        questVillan.setImage(VillianImage);

        for(int i = 0; i < AlfredWelcome.quiz.getQuestions().length ; i++){
            int questionNum = i + 1;
            QuizQuestion questionInfo = AlfredWelcome.quiz.getQuestions()[i];

            // Set up question number label
            Label questionNumLabel = new Label(questionNum + ".");
            questionNumLabel.setPrefWidth(20);
            questionNumLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // Set up question field
            Label questionField = new Label(questionInfo.getQuestion());
            questionField.setWrapText(true);
            questionField.setPrefWidth(300);
            questionField.setPrefHeight(150);
            questionField.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1; \n" +
                    "-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 20;");
            questionField.setAlignment(Pos.TOP_LEFT);

            // Set up answer field
            VBox answerField = new VBox();
            answerField.setPrefWidth(300);
            answerField.setPrefHeight(150);
            answerField.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1; \n" +
                    "-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 20;");
            answerField.setAlignment(Pos.TOP_LEFT);

            Label correctAns = new Label(questionInfo.getCorrectAnswer());
            correctAns.setWrapText(true);
            correctAns.setPadding(new Insets(0, 0, 10, 0));
            correctAns.setStyle("-fx-text-fill: green;");
            Label yourAns = new Label(questionInfo.getUserAnswer());
            yourAns.setWrapText(true);

            Label correctAnsLabel = new Label("Correct Answer:");
            correctAnsLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: green;");
            Label yourAnsLabel = new Label("Your Answer:");
            yourAnsLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

            answerField.getChildren().addAll(
                    correctAnsLabel,
                    correctAns,
                    yourAnsLabel,
                    yourAns
            );

            // Add the row to questionsContainer
            HBox questionRow = new HBox(30);
            questionRow.getChildren().add(questionNumLabel);
            questionRow.getChildren().add(questionField);
            questionRow.getChildren().add(answerField);
            questionRow.setPrefWidth(780);
            questionRow.setAlignment(Pos.TOP_CENTER);

            questionsContainer.getChildren().add(questionRow);
        }
    }

    /**
     * Navigates back to the quest page when 'Continue' button pressed
     * @throws IOException If there is an error while completing the action
     */
    @FXML
    private void onContinue() throws IOException {
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}
