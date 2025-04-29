package com.example.cab302week4.controller;

import com.example.cab302week4.model.IAlfredDAO;
import com.example.cab302week4.model.SqliteAlfredDAO;
import com.example.cab302week4.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;



public class LoginController {
    private User user;
    private SqliteAlfredDAO alfredDAO;


    @FXML
    private void initialize() {
        user = new User();
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username);
        System.out.println(password);
        int FindUser = alfredDAO.getUserID(username,password);

        String Message = user.Login(FindUser);
        messageLabel.setText(Message);
    }
}