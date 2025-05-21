package com.example.alfredAI;

import com.example.alfredAI.model.Quiz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class that starts and runs the application
 */
public class AlfredWelcome extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Alfred AI";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static Quiz quiz;
    public static int currentUserID;
    public static int currentQuestID;

    /**
     * Starts up the application
     * @param stage The application stage
     * @throws IOException If there's an error when starting the app
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AlfredWelcome.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method which launches the application
     * @param args Any arguments passed in
     */
    public static void main(String[] args) {
        launch();
    }
}

