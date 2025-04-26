package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.Flashcard;
import com.example.cab302week4.model.Quiz;
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

public class QuizController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox questionsContainer;

    public QuizController() {
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
        HelloApplication.quiz = new Quiz(flashcards, 2);
    }

    @FXML
    public void initialize() throws IOException {
        progressBar.setProgress(0);

        for(int i = 0; i < HelloApplication.quiz.getQuestions().length ; i++){
            // Set up question number label
            int questionNum = i + 1;
            Label questionNumLabel = new Label(questionNum + ".");

            // Set up question field
            Label question = new Label(HelloApplication.quiz.getQuestions()[i].getQuestion());
            question.setPrefWidth(200);
            question.setPrefHeight(100);
            question.setAlignment(Pos.TOP_LEFT);

            // Set up user answer field
            TextField userAnswerField = new TextField();
            userAnswerField.setPrefWidth(200);
            userAnswerField.setPrefHeight(100);
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
        HelloApplication.quiz.enterUserAnswer(questionNum, userAnswer);
        double quizProgress = HelloApplication.quiz.calcQuizProgress();
        progressBar.setProgress(quizProgress);
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onSubmit() throws IOException {
        // Calculate and update quiz result
        HelloApplication.quiz.calcQuizResult();

        // Show results window
        Stage stage = (Stage) questionsContainer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("quiz-results-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
