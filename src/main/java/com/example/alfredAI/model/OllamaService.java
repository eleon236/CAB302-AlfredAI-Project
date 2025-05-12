package com.example.alfredAI.model;

import com.google.gson.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OllamaService {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final String MODEL = "llama3";

    public List<Flashcard> generateFlashcards(String topic) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String prompt = String.format("""
    Generate 3 flashcards based on the input: "%s". Return only a JSON array of objects in this format:
    [{"question": "Question text", "answer": "Answer text"}]
    Ensure each "answer" is 15 words or less.
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

        System.out.println("OLLAMA RAW RESPONSE (Service): " + response.body()); // Debug log

        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!responseJson.has("response") || responseJson.get("response").isJsonNull()) {
            System.err.println("Error (Service): 'response' field is missing or null in Ollama output.");
            return null; // Or throw an exception
        }

        String responseContent = responseJson.get("response").getAsString().trim();

        int startIndex = responseContent.indexOf('[');
        int endIndex = responseContent.lastIndexOf(']');

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            System.err.println("Error (Service): Could not find valid JSON array within the response.");
            System.err.println("Response content (Service): " + responseContent);
            return null; // Or throw an exception
        }

        String jsonArrayString = responseContent.substring(startIndex, endIndex + 1);
        List<Flashcard> flashcardList = new ArrayList<>();

        try {
            JsonElement parsedElement = JsonParser.parseString(jsonArrayString);
            if (!parsedElement.isJsonArray()) {
                System.err.println("Error (Service): Expected JSON array, got: " + parsedElement);
                return null; // Or throw an exception
            }

            JsonArray flashcards = parsedElement.getAsJsonArray();

            for (JsonElement element : flashcards) {
                if (!element.isJsonObject()) continue;

                JsonObject fc = element.getAsJsonObject();
                String question = fc.has("question") ? fc.get("question").getAsString() : "No question";
                String answer = fc.has("answer") ? fc.get("answer").getAsString() : "No answer";

                flashcardList.add(new Flashcard(question, answer));
            }
        } catch (JsonParseException e) {
            System.err.println("Error (Service) parsing the flashcard JSON: " + jsonArrayString);
            e.printStackTrace();
            return null; // Or throw an exception
        }

        return flashcardList;
    }
}