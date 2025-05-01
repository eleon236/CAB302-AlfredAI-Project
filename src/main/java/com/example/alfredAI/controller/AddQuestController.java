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

public class AddQuestController {
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private DatePicker subjectEndDateTextField;

    @FXML
    private void onAddSubject() throws IOException {
        String subjectName = subjectNameTextField.getText().trim();
        LocalDate subjectEndDate = subjectEndDateTextField.getValue();

        if (subjectName.isEmpty() || subjectEndDate == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        // Create a Quest object with a placeholder ID
        Quest newSubject = new Quest(0, subjectName, subjectEndDate);

        // Save the subject as a quest in the database
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();
        int newQuestID = alfredDAO.addQuest("Null", subjectName, java.sql.Date.valueOf(subjectEndDate));
        newSubject.setId(newQuestID);
        AlfredWelcome.currentQuestID = newQuestID;
        //System.out.println("Subject added: " + newSubject);

        // Navigate to the Quest page
        FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quest-page-view.fxml"));
        Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
        Stage stage = (Stage) subjectNameTextField.getScene().getWindow();
        stage.setScene(scene);
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