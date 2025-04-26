package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.Contact;
import com.example.cab302week4.model.IContactDAO;
import com.example.cab302week4.model.QuizQuestion;
import com.example.cab302week4.model.SqliteContactDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizController {
    @FXML
    private Label questionLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox questionsContainer;

//    private IContactDAO contactDAO;
//    public QuizController() {
//        contactDAO = new SqliteContactDAO();
//    }

    @FXML
    public void initialize() {
        Label[] testQuestions = new Label[] {
                new Label("Question 1?"),
                new Label("Question 2?"),
                new Label("Question 3?"),
                new Label("Question 4?"),
                new Label("Question 5?")
        };

        for(int i = 0; i < testQuestions.length ; i++){
            Label questionNum = new Label((i + 1) + ".");
            TextField userAnswerField = new TextField("A: ");

            HBox questionRow = new HBox(50);
            questionRow.getChildren().add(questionNum);
            questionRow.getChildren().add(testQuestions[i]);
            questionRow.getChildren().add(userAnswerField);

            questionsContainer.getChildren().add(questionRow);
        }
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) questionLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
