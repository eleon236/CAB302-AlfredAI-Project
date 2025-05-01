package com.example.cab302week4.controller;

import com.example.cab302week4.AlfredWelcome;
import com.example.cab302week4.HelloApplication;
//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button goButton;

    @FXML
    private Button backButton;

    @FXML
    private void onbackButton() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }


    @FXML
    private void handleGoButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Error", "Passwords do not match!");
        } else if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Warning", "Please fill all fields.");
        } else {
            showAlert(AlertType.INFORMATION, "Success", "Registration successful for " + username + "!");
            // Here you can save user info or move to another screen
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
