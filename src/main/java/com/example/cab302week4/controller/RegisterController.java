package com.example.cab302week4.controller;

import com.example.cab302week4.model.IAlfredDAO;
import com.example.cab302week4.model.SqliteAlfredDAO;
import com.example.cab302week4.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.Objects;

public class RegisterController {
    private User user;
    private IAlfredDAO alfredDAO;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        user = new User();
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    private void handleRegister(){
        String username = usernameField.getText();
        String password = passwordField1.getText();
        String password2 = passwordField2.getText();

        int FindUser = alfredDAO.getUserID(username,password);

        if(Objects.equals(password, password2)){
            String Message = user.Register(FindUser);
            messageLabel.setText(Message);

            if(FindUser == 0){
                alfredDAO.addUser(username,password);
            }
        }else{
            messageLabel.setText("Passwords do not match");
        }

    }
}

