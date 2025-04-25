import com.example.cab302week4.model.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class QuizTest {
    private Quiz quiz;

    private Flashcard[] flashcards = {
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
    };

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
        quiz = new Quiz(new Flashcard[0], 2);
        assertEquals(0, quiz.getQuestions().length);
    }

    @Test
    public void testSetUpWithAllFlashcardsMastered() {
        Flashcard[] masteredFlashcards = flashcards;
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
        Flashcard[] ThirtyOneFlashcards = new Flashcard[31];
        for (int i = 0; i < ThirtyOneFlashcards.length; i++) {
            ThirtyOneFlashcards[i] = new Flashcard(1, "Why?", "because", false);
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
    public void testCalcQuizProgressShouldBeTwenty() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "something");
        quiz.enterUserAnswer(2, "nothing");
        assertEquals(40, quiz.calcQuizProgress());
    }

    @Test
    public void testCalcQuizProgressShouldBeOneHundred() {
        quiz = new Quiz(flashcards, 2);
        quiz.enterUserAnswer(1, "something");
        quiz.enterUserAnswer(2, "nothing");
        quiz.enterUserAnswer(3, "nothing");
        quiz.enterUserAnswer(4, "nothing");
        quiz.enterUserAnswer(5, "nothing");
        assertEquals(100, quiz.calcQuizProgress());
    }

    @Test
    public void testCalcQuizResult() {

    }
}