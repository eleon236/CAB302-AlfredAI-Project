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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizResultsController {
    @FXML
    private Label resultsLabel;
    @FXML
    private VBox questionsContainer;

    private IAlfredDAO alfredDAO;

    public QuizResultsController() {
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    public void initialize() {
        resultsLabel.setText(alfredDAO.getQuest(AlfredWelcome.currentQuestID).getLastQuizScore());

        if (AlfredWelcome.quiz == null) {
            questionsContainer.getChildren().add(new Label("Come back again tomorrow to do your next daily quiz!"));
            questionsContainer.setAlignment(Pos.CENTER);
            return;
        }

        for(int i = 0; i < AlfredWelcome.quiz.getQuestions().length ; i++){
            int questionNum = i + 1;
            QuizQuestion questionInfo = AlfredWelcome.quiz.getQuestions()[i];

            // Set up question number label
            Label questionNumLabel = new Label(questionNum + ".");
            questionNumLabel.setPrefWidth(50);

            // Set up question field
            Label questionField = new Label(questionInfo.getQuestion());
            questionField.setWrapText(true);
            questionField.setPrefWidth(300);
            questionField.setPrefHeight(150);
            questionField.setAlignment(Pos.TOP_LEFT);

            // Set up answer field
            VBox answerField = new VBox();
            answerField.setPrefWidth(300);
            answerField.setPrefHeight(150);
            answerField.setAlignment(Pos.TOP_LEFT);

            Label correctAns = new Label(questionInfo.getCorrectAnswer());
            correctAns.setWrapText(true);
            correctAns.setPadding(new Insets(0, 0, 10, 0));
            Label yourAns = new Label(questionInfo.getUserAnswer());
            yourAns.setWrapText(true);

            answerField.getChildren().addAll(
                    new Label("Correct Answer:"),
                    correctAns,
                    new Label("Your Answer:"),
                    yourAns
            );

            // Add the row to questionsContainer
            HBox questionRow = new HBox(50);
            questionRow.getChildren().add(questionNumLabel);
            questionRow.getChildren().add(questionField);
            questionRow.getChildren().add(answerField);

            questionsContainer.getChildren().add(questionRow);
        }
    }

    @FXML
    private void onContinue() throws IOException {
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}
