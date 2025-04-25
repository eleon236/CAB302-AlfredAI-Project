import com.example.cab302week4.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class QuizTest {
    private Quiz quiz;

    // Flashcard: ID, question, answer, mastered
    private Flashcard[] flashcards = {
            new Flashcard(1, "What is something?", "something", false),
            new Flashcard(2, "What is nothing?", "nothing", true),
            new Flashcard(3, "Who are you?", "you", true),
            new Flashcard(4, "How does something happen?", "something", false),
            new Flashcard(5, "When did this happen?", "time", false),
            new Flashcard(6, "Where is something?", "place", false),
            new Flashcard(7, "Why does this happen?", "a reason", false),
            new Flashcard(8, "Why does something not happen?", "because", false)
    };

    @BeforeEach
    public void setUp() {
        quiz = new Quiz(flashcards, 5);
    }

    @Test
    public void testSetUp() {
        QuizQuestion[] quizTestQuestions = new QuizQuestion[]{
                new QuizQuestion("What is something?", "something"),
                new QuizQuestion("How does something happen?", "something"),
                new QuizQuestion("When did this happen?", "time"),
                new QuizQuestion("Where is something?", "place"),
                new QuizQuestion("Why does this happen?", "a reason")
        };

        for (int i = 0; i < quiz.getQuestions().length; i++) {
            assertEquals(quizTestQuestions[i].toString(), quiz.getQuestions()[i].toString());
        }
    }
}