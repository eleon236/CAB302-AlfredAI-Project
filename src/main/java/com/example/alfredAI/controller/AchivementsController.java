package com.example.alfredAI.controller;

        import com.example.alfredAI.AlfredWelcome;
        import com.example.alfredAI.model.Achivements;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.layout.FlowPane;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;

        import java.io.IOException;

public class AchivementsController {

            private final Achivements achivements;

            @FXML
            private FlowPane achievementsBox;

            public AchivementsController() {
                this.achivements = new Achivements();
            }

            public void ensureUserInAchievements() {
                achivements.ensureUserInAchievements();
            }

            public void addUserDays() {
                achivements.addUserDays();
            }

            public void updateQuizCompleted() {
                achivements.updateQuizCompleted();
            }

            @FXML
            private Label totalDaysLabel;

            @FXML
            private Label totalQuizzesLabel;

            /**
             * Initializes the controller class. This method is called after the FXML file has been loaded.
             * It sets up the achievements display based on the user's progress.
             */
            @FXML
            public void initialize() {
                Achivements achivements = new Achivements();
                int daysLoggedIn = achivements.getDaysLoggedIn();
                int quizzesCompleted = achivements.getQuizzesCompleted();

                    totalDaysLabel.setText("Total Days Logged In: " + daysLoggedIn);
                    totalQuizzesLabel.setText("Total Quizzes Completed: " + quizzesCompleted);

                    // Define achievements
                String[] dayAchievements = {
                    "Log in for 5 days",
                    "Log in for 10 days",
                    "Log in for 15 days",
                    "Log in for 20 days"
                };
                int[] dayThresholds = {5, 10, 15, 20};

                String[] quizAchievements = {
                    "Complete 5 quizzes",
                    "Complete 10 quizzes",
                    "Complete 15 quizzes",
                    "Complete 20 quizzes"
                };
                int[] quizThresholds = {5, 10, 15, 20};

                // Add day achievements
                for (int i = 0; i < dayAchievements.length; i++) {
                    Label label = new Label(dayAchievements[i]);
                    if (daysLoggedIn >= dayThresholds[i]) {
                        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                    } else {
                        label.setStyle("-fx-text-fill: gray;");
                    }
                    achievementsBox.getChildren().add(label);
                }

                // Add quiz achievements
                for (int i = 0; i < quizAchievements.length; i++) {
                    Label label = new Label(quizAchievements[i]);
                    if (quizzesCompleted >= quizThresholds[i]) {
                        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                    } else {
                        label.setStyle("-fx-text-fill: gray;");
                    }
                    achievementsBox.getChildren().add(label);
                }
            }

            @FXML
            private Button backButton;

    /**
     * This method is called when the back button is clicked.
     * @throws IOException
     */
    @FXML
    private void onBack() throws IOException {
                Stage stage = (Stage) backButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(AlfredWelcome.class.getResource("quests-view.fxml"));
                Scene scene = new Scene(loader.load(), AlfredWelcome.WIDTH, AlfredWelcome.HEIGHT);
                stage.setScene(scene);
            }
        }