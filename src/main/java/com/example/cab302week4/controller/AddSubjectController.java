package com.example.cab302week4.controller;

import com.example.cab302week4.HelloApplication;
import com.example.cab302week4.model.AddSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDate;

public class AddSubjectController {
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private DatePicker subjectEndDateTextField;
    @FXML
    private DatePicker subjectlastQuizDateTextField;

    @FXML
    private void onAddSubject() {
        String subjectName = subjectNameTextField.getText().trim();
        LocalDate subjectEndDate = subjectEndDateTextField.getValue();
        LocalDate subjectLastQuizDate = subjectlastQuizDateTextField.getValue();

        if (subjectName.isEmpty() || subjectEndDate == null || subjectLastQuizDate == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        // Create an AddSubject object
        AddSubject newSubject = new AddSubject(subjectName, subjectEndDate, subjectLastQuizDate);

        // TODO: Add logic to save the subject to the database or list
        System.out.println("Subject added: " + newSubject);

        closeWindow();
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