package com.example.alfredAI.controller;

import com.example.alfredAI.AlfredWelcome;
import com.example.alfredAI.model.Flashcard;
import com.example.alfredAI.model.SqliteAlfredDAO;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class OllamaClientController {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final String MODEL = "llama3";

    @FXML
    private TextField topicTextField;

    @FXML
    private ListView<String> flashcardListView; // Use ListView
    // @FXML
    // private TextArea flashcardTextArea; // Or use TextArea

    private ObservableList<String> flashcardItems = FXCollections.observableArrayList(); // For ListView

    @FXML
    public void initialize() {
        if (flashcardListView != null) {
            flashcardListView.setItems(flashcardItems); // Set items for ListView
        }
        // If using TextArea, no initialization needed here for display
    }

    @FXML
    private void generateFromInput(ActionEvent event) {
        String topic = topicTextField.getText();
        if (topic != null && !topic.trim().isEmpty()) {
            generateFlashcard(topic.trim());
        } else {
            // Optionally show an error message to the user
            System.err.println("Please enter a topic.");
        }
    }

    public void generateFlashcard(String topic) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String prompt = String.format("""
                Generate 3 flashcards based from input: "%s". Return only a JSON array of objects in this format:
                [{"question": "Question text", "answer": "Answer text"}]
                """, topic);

            JsonObject body = new JsonObject();
            body.addProperty("model", MODEL);
            body.addProperty("prompt", prompt);
            body.addProperty("stream", false);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("OLLAMA RAW RESPONSE: " + response.body()); // Debug log

            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!responseJson.has("response") || responseJson.get("response").isJsonNull()) {
                System.err.println("Error: 'response' field is missing or null in Ollama output.");
                return;
            }

            String responseContent = responseJson.get("response").getAsString().trim();

            int startIndex = responseContent.indexOf('[');
            int endIndex = responseContent.lastIndexOf(']');

            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                System.err.println("Error: Could not find valid JSON array within the response.");
                System.err.println("Response content: " + responseContent);
                return;
            }

            String jsonArrayString = responseContent.substring(startIndex, endIndex + 1);

            try {
                JsonElement parsedElement = JsonParser.parseString(jsonArrayString);
                if (!parsedElement.isJsonArray()) {
                    System.err.println("Error: Expected JSON array, got: " + parsedElement);
                    return;
                }

                JsonArray flashcards = parsedElement.getAsJsonArray();
                SqliteAlfredDAO dao = new SqliteAlfredDAO();
                flashcardItems.clear(); // Clear previous flashcards in the UI

                for (JsonElement element : flashcards) {
                    if (!element.isJsonObject()) continue;

                    JsonObject fc = element.getAsJsonObject();
                    String question = fc.has("question") ? fc.get("question").getAsString() : "No question";
                    String answer = fc.has("answer") ? fc.get("answer").getAsString() : "No answer";

                    dao.addFlashcard(AlfredWelcome.currentQuestID, new Flashcard(question, answer));

                    // Add to UI display (using ListView)
                    if (flashcardListView != null) {
                        flashcardItems.add("Q: " + question + "\nA: " + answer);
                    }
                    // If using TextArea:
                    // if (flashcardTextArea != null) {
                    //     flashcardTextArea.appendText("Q: " + question + "\nA: " + answer + "\n\n");
                    // }
                }
            } catch (JsonParseException e) {
                System.err.println("Error parsing the flashcard JSON: " + jsonArrayString);
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}