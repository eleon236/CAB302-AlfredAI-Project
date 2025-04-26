package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.Flashcard;
import com.example.cab302week4.model.Quiz;
import com.example.cab302week4.model.QuizQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private final Quiz quiz;

    public QuizResultsController() {
        // TODO Remove after adding DB
        Flashcard[] flashcards = {
                new Flashcard(1, "What is something?", "something", false),
                new Flashcard(2, "What is nothing?", "nothing", true),
                new Flashcard(3, "Who are you?", "you", true),
                new Flashcard(4, "How does something happen?", "something", false),
                new Flashcard(5, "When did this happen?", "time", false),
                new Flashcard(6, "Where is something?", "place", false),
                new Flashcard(7, "Why does this happen?", "a reason", false),
                new Flashcard(8, "Why does something not happen?", "because", false),
                new Flashcard(9, "Who is that?", "someone", false),
                new Flashcard(10, "Why?", "just because", false)
        };
        quiz = new Quiz(flashcards, 2);
    }

    @FXML
    public void initialize() {
        resultsLabel.setText(quiz.getResult() + " / " + quiz.getQuestions().length);

        for(int i = 0; i < quiz.getQuestions().length ; i++){
            int questionNum = i + 1;
            QuizQuestion questionInfo = quiz.getQuestions()[i];

            // Set up question number label
            Label questionNumLabel = new Label(questionNum + ".");

            // Set up question field
            Label questionField = new Label(questionInfo.getQuestion());
            questionField.setPrefWidth(200);
            questionField.setPrefHeight(100);
            questionField.setAlignment(Pos.TOP_LEFT);

            // Set up answer field
            VBox answerField = new VBox();
            answerField.setPrefWidth(200);
            answerField.setPrefHeight(100);
            answerField.setAlignment(Pos.TOP_LEFT);

            answerField.getChildren().addAll(
                    new Label("Correct Answer:"),
                    new Label(questionInfo.getCorrectAnswer()),
                    new Label("Your Answer:"),
                    new Label(questionInfo.getUserAnswer())
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
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
