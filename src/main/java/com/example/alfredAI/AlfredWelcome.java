package com.example.alfredAI;

import com.example.alfredAI.model.Quiz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// TODO
// Make going back to main page work
// Make going back to quest page work
// Make quests page refresh when returning

//Main class
public class AlfredWelcome extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Alfred AI";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static Quiz quiz;
    public static int currentUserID;
    public static int currentQuestID;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AlfredWelcome.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

