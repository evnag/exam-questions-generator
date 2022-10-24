package ru.skypro.examquestionsgenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.QuestionIsAlreadyAddedException;
import ru.skypro.examquestionsgenerator.exception.QuestionNotFoundException;
import ru.skypro.examquestionsgenerator.service.JavaQuestionService;
import ru.skypro.examquestionsgenerator.service.QuestionService;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void cleanUpEach() {
        questionService.getAll().forEach(questionService::remove);
    }

    @Test
    public void addQuestionPositiveTest() {
        addQuestionWithCheck();
    }

    private Question addQuestionWithCheck(String question, String answer) {
        Question expected = new Question(question, answer);
        int sizeBefore = questionService.getAll().size();
        questionService.add(expected.getQuestion(), expected.getAnswer());
        assertThat(questionService.getAll())
                .asList()
                .isNotEmpty()
                .hasSize(sizeBefore + 1)
                .contains(expected);
        return expected;
    }

    private Question addQuestionWithCheck() {
        return addQuestionWithCheck("Question", "Answer");
    }

    @Test
    public void addQuestionNegativeTest() {
        Question question = addQuestionWithCheck();
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add(question));
    }

    @Test
    public void addQuestionNegative2Test() {
        Question question = addQuestionWithCheck();
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add(question.getQuestion(), question.getAnswer()));
    }

    @Test
    public void removeQuestionPositiveTest() {
        Question question1 = addQuestionWithCheck("Question1", "Answer1");
        Question question2 = addQuestionWithCheck("Question2", "Answer2");
        questionService.remove(question1);
        assertThat(questionService.getAll())
                .asList()
                .isNotEmpty()
                .hasSize(1)
                .contains(question2);
        questionService.remove(question2);
        assertThat(questionService.getAll())
                .asList()
                .isEmpty();
    }

    @Test
    public void removeQuestionNegativeTest() {
        Question testQuestion = new Question("questionTest", "answerTest");
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(testQuestion));
        addQuestionWithCheck("Question1", "Answer1");
        addQuestionWithCheck("Question2", "Answer2");
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(testQuestion));
    }

    @Test
    public void getAllTest() {
        List<Question> questionList = Arrays.asList(
                new Question("Question1", "Answer1"),
                new Question("Question2", "Answer2")
        );

        questionList.forEach(questionService::add);
        assertThat(questionService.getAll())
                .asList()
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new Question("Question1", "Answer1"),
                                new Question("Question2", "Answer2")
                        )
                );
    }

    @Test
    public void getRandomQuestionPositiveTest() {
        List<Question> questionList = Arrays.asList(
                new Question("Question1", "Answer1"),
                new Question("Question2", "Answer2"),
                new Question("Question3", "Answer3")
        );
        Question expected = new Question("Question2", "Answer2");
        questionList.forEach(questionService::add);

        assertThat(questionService.getAll())
                .asList().contains(expected);
    }

    @Test
    public void getRandomQuestionNegativeTest() {
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(questionService::getRandomQuestion);
    }

}
