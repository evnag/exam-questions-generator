package ru.skypro.examquestionsgenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.IllegalAmountOfQuestionException;
import ru.skypro.examquestionsgenerator.service.ExaminerServiceImpl;
import ru.skypro.examquestionsgenerator.service.JavaQuestionService;
import ru.skypro.examquestionsgenerator.service.MathQuestionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {
    @Mock
    private JavaQuestionService javaQuestionService;

    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Set<Question> javaQuestion = new HashSet<>();
    private final Set<Question> mathQuestion = new HashSet<>();

    @BeforeEach
    public void before() {
        javaQuestion.clear();
        mathQuestion.clear();

        javaQuestion.addAll(
                Stream.of(
                        new Question("Question1", "Answer1"),
                        new Question("Question2", "Answer2"),
                        new Question("Question3", "Answer3")
                ).collect(Collectors.toSet())
        );

        mathQuestion.addAll(
                Stream.of(
                        new Question("MathQuestion1", "MathAnswer1"),
                        new Question("MathQuestion2", "MathAnswer2"),
                        new Question("MathQuestion3", "MathAnswer3")
                ).collect(Collectors.toSet())
        );
        when(javaQuestionService.getAll()).thenReturn(javaQuestion);
        when(mathQuestionService.getAll()).thenReturn(mathQuestion);
    }

    @Test
    public void getQuestionsPositiveTest() {
        Random random = mock(MyRandom.class);
        when(random.nextInt(anyInt())).thenReturn(1, 0, 0, 0, 0, 0, 1, 1, 1);
        ReflectionTestUtils.setField(examinerService, "random", random);
        when(javaQuestionService.getRandomQuestion()).thenReturn(
                new Question("Question1", "Answer1"),
                new Question("Question2", "Answer2"),
                new Question("Question1", "Answer1"),
                new Question("Question1", "Answer1"),
                new Question("Question3", "Answer3")
        );
        when(mathQuestionService.getRandomQuestion()).thenReturn(
                new Question("MathQuestion2", "MathAnswer2"),
                new Question("MathQuestion2", "MathAnswer2"),
                new Question("MathQuestion2", "MathAnswer2"),
                new Question("MathQuestion1", "MathAnswer1")
        );

        assertThat(examinerService.getQuestions(5))
                .hasSize(5)
                .containsExactly(
                        new Question("Question1", "Answer1"),
                        new Question("Question2", "Answer2"),
                        new Question("Question3", "Answer3"),
                        new Question("MathQuestion2", "MathAnswer2"),
                        new Question("MathQuestion1", "MathAnswer1")
                );
    }

    @Test
    public void getQuestionsNegativeTest() {

        assertThatExceptionOfType(IllegalAmountOfQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(-1));

        assertThatExceptionOfType(IllegalAmountOfQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(javaQuestion.size() + mathQuestion.size() + 1));
    }

    public static class MyRandom extends Random {

    }
}
