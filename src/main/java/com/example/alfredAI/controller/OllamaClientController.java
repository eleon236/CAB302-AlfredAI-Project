package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import com.google.gson.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class OllamaClientController {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final String MODEL = "llama3";

    @FXML
    private void generate(ActionEvent event) {
        String topic = "AI generated Flashcard";
        generateFlashcard(topic);
    }

    public void generateFlashcard(String topic) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Step 1: Create structured prompt
            String prompt = String.format("""
                    Generate 3 flashcards based from input: "%s". Return only a JSON array of objects in this format:
                    [{"question": "Question text", "answer": "Answer text"}]
                    """, topic);

            JsonObject body = new JsonObject();
            body.addProperty("model", MODEL);
            body.addProperty("prompt", prompt);
            body.addProperty("stream", false); // important for easier parsing

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            String jsonContent = responseJson.get("response").getAsString().trim();

            // Step 2: Parse the JSON array of flashcards
            JsonArray flashcards = JsonParser.parseString(jsonContent).getAsJsonArray();
            SqliteAlfredDAO dao = new SqliteAlfredDAO();

            for (JsonElement element : flashcards) {
                JsonObject fc = element.getAsJsonObject();
                String question = fc.get("question").getAsString();
                String answer = fc.get("answer").getAsString();

                dao.addFlashcard(AlfredWelcome.currentQuestID, new Flashcard(question, answer));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quest-page-view.fxml"));
        Parent root = loader.load();

        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }


}
