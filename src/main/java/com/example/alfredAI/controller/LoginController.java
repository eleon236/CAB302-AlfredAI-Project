package com.example.alfredAI.controller;

import com.example.alfredAI.model.SqliteAlfredDAO;
import com.example.alfredAI.model.User;
import javafx.fxml.FXML;
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

        if(FindUser == 0){
            messageLabel.setText("This combination of username and password is invalid");
        }else{
            user.LogedinUser(FindUser);
            messageLabel.setText("You have sucessfully logged in");
        }
    }
}