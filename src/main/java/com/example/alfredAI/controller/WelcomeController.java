package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.SqliteAlfredDAO;
import com.example.alfredAI.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller class for home / login page for linking functions from the welcome-view XML file
 */
public class WelcomeController {
    private User user;
    private SqliteAlfredDAO alfredDAO;

    @FXML
    private VBox emptyBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView bearImageView;

    /**
     * Initialise key elements of program such as the database, setting images and setting title lable
     */
    @FXML
    public void initialize() {
        user = new User();
        alfredDAO = new SqliteAlfredDAO();

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/alfred.png")));
        bearImageView.setImage(image);

        titleLabel.setText("Welcome to\nAlfred AI!");
    }

    /**
     * Takes user to register page via a button at the bottom of the UI
     * @throws IOException required in case errors occur in the xml file
     */
    @FXML
    private void onRegister() throws IOException {
        Stage stage = (Stage) emptyBox.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Checks username and password to check if its in database user is either logged in and taken to their quests page
     * if not successful user is required to reenter values to the same values in the databse
     * @throws IOException required in case errors occur in the xml file
     */
    @FXML
    private void onContinue() throws IOException {
        Stage stage = (Stage) emptyBox.getScene().getWindow();

        //Get attributes users entered
        String username = usernameField.getText();
        String password = passwordField.getText();

        //run login function and check database
        int FoundUserID = alfredDAO.getUserID(username,password);
        String Message = user.Login(FoundUserID);
        //display message
        messageLabel.setText(Message);


//        Show the message for a seconds before changing page
        //Set delay
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            try {
                if(Message.equals("You have successfully logged in")){
                    // Update currentUserID
                    AlfredWelcome.currentUserID = FoundUserID;
                    // Load the next scene after delay
                    FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
                    Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
                    stage.setScene(scene);
                }
            } catch (IOException e) {
                //handel loading error
                e.printStackTrace();
            }
        });
        // Start the delay
        delay.play();
    }
}
