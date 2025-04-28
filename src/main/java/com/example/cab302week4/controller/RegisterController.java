package com.example.cab302week4.controller;

import com.example.cab302week4.model.IAlfredDAO;
import com.example.cab302week4.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.Objects;

public class RegisterController {
    private IAlfredDAO alfredDAO;
    public int LoggedinUserID;

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
        alfredDAO = new SqliteAlfredDAO();
    }

    @FXML
    private void handleRegister(){
        String username = usernameField.getText();
        String password = passwordField1.getText();
        String password2 = passwordField2.getText();

        int FindUser = alfredDAO.getUserID(username,password);

        if(FindUser == 0){
            if(Objects.equals(password, password2)){
                messageLabel.setText("You have successfully registered an account");
                alfredDAO.addUser(username,password);
            }else{
                messageLabel.setText("Passwords do not match");
            }
        }else{
            messageLabel.setText("This user already exsits");
        }
    }
}

