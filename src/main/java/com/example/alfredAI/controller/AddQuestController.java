package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Quest;
import com.example.alfredAI.model.SqliteAlfredDAO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AddQuestController {
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private DatePicker subjectEndDateTextField;
    @FXML private ImageView alfred;
    @FXML private ImageView bob;
    @FXML private ImageView harry;
    @FXML private ImageView milo;
    @FXML private ImageView lola;
    @FXML private ImageView penny;
    @FXML private ImageView steve;
    @FXML private ImageView capi;
    @FXML private ImageView oscar;
    @FXML private ImageView molly;

    @FXML
    public void initialize() {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/alfredforselection.png")));
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/bob.png")));
        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/harry.png")));
        Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/milo.png")));
        Image image5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/lola.png")));
        Image image6 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/penny.png")));
        Image image7 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/steve.png")));
        Image image8 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/capi.png")));
        Image image9 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/oscar.png")));
        Image image10 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/molly.png")));
        alfred.setImage(image1);
        bob.setImage(image2);
        harry.setImage(image3);
        milo.setImage(image4);
        lola.setImage(image5);
        penny.setImage(image6);
        steve.setImage(image7);
        capi.setImage(image8);
        oscar.setImage(image9);
        molly.setImage(image10);
    }

    public void handleSelection(javafx.scene.input.MouseEvent mouseEvent) {
        VBox selectedBox = (VBox) mouseEvent.getSource();
        ImageView selectedImage = (ImageView) selectedBox.getChildren().get(0);
        Label selectedLabel = (Label) selectedBox.getChildren().get(1);

        // Reset all image styles (optional: store them in a list if needed)
        resetImageStyles();
        // Highlight the selected image
        selectedImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 120, 255, 0.7), 15, 0.3, 0, 0);");
    }

    // Optional helper to remove selection styles (could be refined with a list of imageViews)
    private void resetImageStyles() {
        alfred.setStyle("");
        bob.setStyle("");
        harry.setStyle("");
        milo.setStyle("");
        lola.setStyle("");
        penny.setStyle("");
        steve.setStyle("");
        capi.setStyle("");
        oscar.setStyle("");
        molly.setStyle("");
    }

    @FXML
    private void onAddSubject() throws IOException {
        String subjectName = subjectNameTextField.getText().trim();
        LocalDate subjectEndDate = subjectEndDateTextField.getValue();

        if (subjectName.isEmpty() || subjectEndDate == null) {
            System.out.println("All fields must be filled out.");
            return;
        }

        // Save the subject as a quest in the database
        SqliteAlfredDAO alfredDAO = new SqliteAlfredDAO();
        AlfredWelcome.currentQuestID = alfredDAO.addQuest(AlfredWelcome.currentUserID, "Null", subjectName, java.sql.Date.valueOf(subjectEndDate));

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