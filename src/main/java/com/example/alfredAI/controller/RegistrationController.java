package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.SqliteAlfredDAO;
import com.example.alfredAI.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class that handles the registration of a user
 */
public class RegistrationController {
    private User user;
    private SqliteAlfredDAO alfredDAO;

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

    /**
     * Creates instances for user and link for database
     */
    @FXML
    public void initialize() {
        user = new User();
        alfredDAO = new SqliteAlfredDAO();
    }

    /**
     * Takes users back to the login page
     * @throws IOException required in case errors occur in the xml file
     */
    @FXML
    private void onBackButton() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Handles the registration by checking to see if all fields filled, passwords are the same and to see if the values are not already in the database
     * If all of these are true user is registered and logged in
     * Else user is required to reenter values to all conditions are true
     */
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
            int FindUser = alfredDAO.getUserID(username,password);
            String Message = user.Register(FindUser);
            if(FindUser == 0){
                User user = alfredDAO.addUser(username,password);
                AlfredWelcome.currentUserID = user.getUserID();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful for " + username + "!");

                Optional<ButtonType> result = alert.showAndWait();

                // If OK is pressed, switch to the home screen
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        // Load the home.fxml file
                        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
                        Parent homeRoot = loader.load();

                        // Get the current stage
                        Stage stage = (Stage) usernameField.getScene().getWindow();

                        // Set the new scene
                        stage.setScene(new Scene(homeRoot));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace(); // Log the error if loading fails
                    }
                }

            }else{
                showAlert(AlertType.ERROR, "Error", Message);
            }
        }
    }

    /**
     * Function to slow the alert for invalid register conditions
     * @param type the alert type being displayed that determines the warning image
     * @param title title displayed on the alert
     * @param message error message explaining what the error was e.g. passwords don't match, all fields filled, success or failure.
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
