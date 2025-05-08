package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class QuizController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox questionsContainer;
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

    @FXML
    public void initialize() throws IOException {
        if (noFlashcards) {
            progressBar.setVisible(false);
            submitBtn.setVisible(false);
            questionsContainer.getChildren().add(new Label("There's no flashcards in this quest yet. Come back when you've added some flashcards!"));
            questionsContainer.setAlignment(Pos.CENTER);
            return;
        }

        progressBar.setProgress(0);

        for(int i = 0; i < AlfredWelcome.quiz.getQuestions().length ; i++){
            // Set up question number label
            int questionNum = i + 1;
            Label questionNumLabel = new Label(questionNum + ".");
            questionNumLabel.setPrefWidth(50);

            // Set up question field
            Label question = new Label(AlfredWelcome.quiz.getQuestions()[i].getQuestion());
            question.setWrapText(true);
            question.setPrefWidth(300);
            question.setPrefHeight(150);
            question.setAlignment(Pos.TOP_LEFT);

            // Set up user answer field
            TextField userAnswerField = new TextField();
            userAnswerField.setPrefWidth(300);
            userAnswerField.setPrefHeight(150);
            userAnswerField.setAlignment(Pos.TOP_LEFT);
            userAnswerField.setPromptText("Your answer...");
            userAnswerField.textProperty().addListener((observable, oldValue, newValue) ->
                onAnswer(questionNum, newValue)
            );

            // Add the row to questionsContainer
            HBox questionRow = new HBox(50);
            questionRow.getChildren().add(questionNumLabel);
            questionRow.getChildren().add(question);
            questionRow.getChildren().add(userAnswerField);

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

        // Update distance travelled in the quest
        Quest quest = alfredDAO.getQuest(AlfredWelcome.currentQuestID);
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
