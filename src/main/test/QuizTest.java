}import com.example.alfredAI.model.*;

        import org.junit.jupiter.api.*;

        import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    private Quiz quiz;

    private final List<Flashcard> flashcards = new ArrayList<>( List.of(
            new Flashcard(1, "What is something?", "something", false),
            new Flashcard(2, "What is nothing?", "nothing", true),
            new Flashcard(3, "Who are you?", "you", true),
            new Flashcard(4, "How does something happen?", "something", false),
            new Flashcard(5, "When did this happen?", "time", false),
            new Flashcard(6, "Where is something?", "place", false),
            new Flashcard(7, "Why does this happen?", "a reason", false),
            new Flashcard(8, "Why does something not happen?", "because", false),
            new Flashcard(9, "Who is that?", "someone", false),
            new Flashcard(10, "Why?", "just because", false)
    ));

    @Test
    public void testSetUpWithReasonableDaysLeft() {
        quiz = new Quiz(flashcards, 2);

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

    @Test
    public void testSetUpWithNoFlashcards() {
        quiz = new Quiz(new ArrayList<>(), 2);
        assertEquals(0, quiz.getQuestions().length);
    }

    @Test
    public void testSetUpWithAllFlashcardsMastered() {
        List<Flashcard> masteredFlashcards = flashcards;
        for (Flashcard flashcard : masteredFlashcards) {
            flashcard.setMastered(true);
        }
        quiz = new Quiz(masteredFlashcards, 3);

        assertEquals(5, quiz.getQuestions().length);
    }

    @Test
    public void testSetUpWithZeroDaysLeft() {
        quiz = new Quiz(flashcards, 0);

        assertEquals(8, quiz.getQuestions().length);
    }

    @Test
    public void testSetUpWithLessThan5Questions() {
        quiz = new Quiz(flashcards, 8);

        assertEquals(5, quiz.getQuestions().length);
    }

    @Test
    public void testSetUpWithMoreThan30Questions() {
        List<Flashcard> ThirtyOneFlashcards = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            ThirtyOneFlashcards.add(new Flashcard(1, "Why?", "because", false));
        }

        quiz = new Quiz(ThirtyOneFlashcards, 1);

        assertEquals(30, quiz.getQuestions().length);
    }

    @Test
    public void testEnterUserAnswer() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "nothing");
        assertEquals("nothing", quiz.getQuestions()[0].getUserAnswer());
    }

    @Test
    public void testCalcQuizProgressShouldBeZero() {
        quiz = new Quiz(flashcards, 2);
        assertEquals(0, quiz.calcQuizProgress());
    }

    @Test
    public void testCalcQuizProgressShouldBeMiddle() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "something");
        quiz.enterUserAnswer(2, "nothing");
        assertEquals(0.4, quiz.calcQuizProgress());
    }

    @Test
    public void testCalcQuizProgressShouldBeOne() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "something");
        quiz.enterUserAnswer(2, "nothing");
        quiz.enterUserAnswer(3, "nothing");
        quiz.enterUserAnswer(4, "nothing");
        quiz.enterUserAnswer(5, "nothing");
        assertEquals(1, quiz.calcQuizProgress());
    }

    @Test
    public void testCalcQuizResultShouldBeZero() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "wrong");
        quiz.enterUserAnswer(2, "wrong");
        quiz.enterUserAnswer(3, "wrong");
        quiz.enterUserAnswer(4, "wrong");
        quiz.enterUserAnswer(5, "wrong");
        quiz.calcQuizResult();
        assertEquals(0, quiz.getResult());
    }

    @Test
    public void testCalcQuizResultShouldBeTwo() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "something");
        quiz.enterUserAnswer(2, "something");
        quiz.enterUserAnswer(3, "wrong");
        quiz.enterUserAnswer(4, "wrong");
        quiz.enterUserAnswer(5, "wrong");
        quiz.calcQuizResult();
        assertEquals(2, quiz.getResult());
    }
