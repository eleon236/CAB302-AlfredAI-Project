package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class QuizController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox questionsContainer;
    @FXML
    private ImageView questCharacter;
    @FXML
    private ImageView questVillan;
    @FXML
    private Button submitBtn;

    private final IAlfredDAO alfredDAO;
    private final boolean noFlashcards;

    public QuizController() {
        alfredDAO = new SqliteAlfredDAO();

        List<Flashcard> flashcards = alfredDAO.getQuestFlashcards(AlfredWelcome.currentQuestID);
        noFlashcards = flashcards.isEmpty();

        LocalDate questEndDate = alfredDAO.getQuest(AlfredWelcome.currentQuestID).getEndDate();
        int questDaysLeft = (int) ChronoUnit.DAYS.between(LocalDate.now(), questEndDate);
        AlfredWelcome.quiz = new Quiz(flashcards, questDaysLeft);
    }

    public int generateRandomNumber() {
        int Number = (int)(Math.random() * 8) + 1;
        AlfredWelcome.quiz.setVillainID(Number);
        return(Number);
    }

    @FXML
    public void initialize() throws IOException {
        if (noFlashcards) {
            progressBar.setVisible(false);
            submitBtn.setVisible(false);
            questionsContainer.getChildren().add(new Label("There's no flashcards in this quest yet. Come back when you've added some flashcards!"));
            questionsContainer.setAlignment(Pos.CENTER);
            return;
        }

        String characterName = alfredDAO.getQuest(AlfredWelcome.currentQuestID).getCharacterName();
        // Set character images
        Image characterImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/"+characterName+".png")));
        questCharacter.setImage(characterImage);

        String villainNo = Integer.toString(generateRandomNumber());
        Image VillianImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/villians/villian"+villainNo+".png")));
        questVillan.setImage(VillianImage);

        progressBar.setProgress(0);

        for(int i = 0; i < AlfredWelcome.quiz.getQuestions().length ; i++){
            // Set up question number label
            int questionNum = i + 1;
            Label questionNumLabel = new Label(questionNum + ".");
            questionNumLabel.setPrefWidth(20);
            questionNumLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // Set up question field
            Label question = new Label(AlfredWelcome.quiz.getQuestions()[i].getQuestion());
            question.setWrapText(true);
            question.setPrefWidth(300);
            question.setMaxHeight(150);
            question.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1; \n" +
                    "-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 20;");
            question.setAlignment(Pos.TOP_LEFT);

            // Set up user answer field
            TextField userAnswerField = new TextField();
            userAnswerField.setPrefWidth(300);
            userAnswerField.setPrefHeight(150);
            userAnswerField.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1; \n" +
                    "-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 20;");
            userAnswerField.setAlignment(Pos.TOP_LEFT);
            userAnswerField.setPromptText("Your answer...");
            userAnswerField.textProperty().addListener((observable, oldValue, newValue) ->
                onAnswer(questionNum, newValue)
            );

            // Add the row to questionsContainer
            HBox questionRow = new HBox(30);
            questionRow.getChildren().add(questionNumLabel);
            questionRow.getChildren().add(question);
            questionRow.getChildren().add(userAnswerField);
            questionRow.setPrefWidth(780);
            questionRow.setAlignment(Pos.TOP_CENTER);

            questionsContainer.getChildren().add(questionRow);
        }
    }

    @FXML
    private void onAnswer(int questionNum, String userAnswer) {
        AlfredWelcome.quiz.enterUserAnswer(questionNum, userAnswer);
        double quizProgress = AlfredWelcome.quiz.calcQuizProgress();
        progressBar.setProgress(quizProgress);
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onSubmit() throws IOException {
        // Calculate and update quiz result
        AlfredWelcome.quiz.calcQuizResult();

        // Update current streak days for the quest
        Quest quest = alfredDAO.getQuest(AlfredWelcome.currentQuestID);
        quest.updateQuestStreak();
        alfredDAO.updateQuestStreak(AlfredWelcome.currentQuestID, quest.getCurrentStreakDays());

        // Update distance travelled in the quest
        quest.updateDistanceTravelled();
        alfredDAO.updateQuestDistance(AlfredWelcome.currentQuestID, quest.getDistanceTravelled());

        // Update quiz result in database
        alfredDAO.updateQuestLastQuizData(
                AlfredWelcome.currentQuestID,
                AlfredWelcome.quiz.getResult() + " / " + AlfredWelcome.quiz.getQuestions().length,
                LocalDate.now()
        );

        // Update mastered flashcards in the database
        AlfredWelcome.quiz.updateFlashcardsMastered();

        // Show results window
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quiz-results-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}
