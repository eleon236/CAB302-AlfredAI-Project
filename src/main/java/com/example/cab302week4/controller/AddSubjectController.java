package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AddSubjectController {
    @FXML
    private TextField subjectNameTextField;

    @FXML
    private void onAddSubject() {
        String subjectName = subjectNameTextField.getText().trim();
        if (!subjectName.isEmpty()) {
            // TODO: Add logic to save the subject to the database or list
            System.out.println("Subject added: " + subjectName);
            closeWindow();
        } else {
            System.out.println("Subject name cannot be empty.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) subjectNameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) subjectNameTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}