package com.example.cab302week4.controller;

import com.example.cab302week4.AlfredWelcome;
import com.example.cab302week4.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private VBox emptyBox;

    @FXML
    private ImageView bearImage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        // Load bear image
//        Image bear = new Image("Bear.png"); // Assuming it's in resources folder
//        bearImage.setImage(bear);

        titleLabel.setText("Welcome to\nAlfred AI!");
    }

    @FXML
    private void onRegister() throws IOException {
        Stage stage = (Stage) emptyBox.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onContinue() throws IOException {
        Stage stage = (Stage) emptyBox.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("quests-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}
