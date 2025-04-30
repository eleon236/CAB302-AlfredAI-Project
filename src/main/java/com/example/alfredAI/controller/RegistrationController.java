package com.example.alfredAI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

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
