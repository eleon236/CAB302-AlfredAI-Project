package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.Flashcard;
import com.example.cab302week4.model.Quiz;
import com.example.cab302week4.model.QuizQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
            // Set up question field
            int questionNum = i + 1;
            QuizQuestion questionInfo = quiz.getQuestions()[i];
            Label questionNumLabel = new Label(questionNum + ".");
            Label questionField = new Label(questionInfo.getQuestion());

            // Set up answer field
            VBox answerField = new VBox();
            answerField.getChildren().addAll(
                    new Label("Answer:"),
                    new Label(questionInfo.getCorrectAnswer()),
                    new Label("Your Answer:"),
                    new Label(questionInfo.getUserAnswer())
            );

            // Add the row to questionsContainer
            HBox questionRow = new HBox();
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
