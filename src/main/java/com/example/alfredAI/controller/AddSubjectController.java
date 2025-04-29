package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Quest;
import com.example.alfredAI.model.SqliteAlfredDAO;
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
    private void onAddSubject() {
        String subjectName = subjectNameTextField.getText().trim();
        LocalDate subjectEndDate = subjectEndDateTextField.getValue();

        if (subjectName.isEmpty() || subjectEndDate == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        // Create an Quest object with a placeholder ID
        Quest newSubject = new Quest(0, subjectName, subjectEndDate);

        // Save the subject as a quest in the database
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();
        alfredDAO.addQuest("Null", subjectName, java.sql.Date.valueOf(subjectEndDate));
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
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        stage.setScene(scene);
    }
}