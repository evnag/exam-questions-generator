package ru.skypro.examquestionsgenerator;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.QuestionIsAlreadyAddedException;
import ru.skypro.examquestionsgenerator.exception.QuestionNotFoundException;
import ru.skypro.examquestionsgenerator.repository.JavaQuestionRepository;
import ru.skypro.examquestionsgenerator.service.JavaQuestionService;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    @Mock
    private JavaQuestionRepository javaQuestionRepository;

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    @ParameterizedTest
    @MethodSource("question1")
    public void addTest1(Question question) {
        when(javaQuestionRepository.add(question)).thenReturn(true);
        javaQuestionService.add(question);

        when(javaQuestionRepository.add(question)).thenThrow(new QuestionIsAlreadyAddedException());
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> javaQuestionService.add(question));

        when(javaQuestionRepository.getAll()).thenReturn(Collections.singleton(question));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(question);
    }

    @ParameterizedTest
    @MethodSource("question2")
    public void addTest2(String question, String answer) {
        Question q = new Question(question, answer);
        when(javaQuestionRepository.add(new Question(question, answer))).thenReturn(true);
        javaQuestionService.add(question, answer);

        when(javaQuestionRepository.add(new Question(question, answer))).thenThrow(new QuestionIsAlreadyAddedException());
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> javaQuestionService.add(question, answer));

        when(javaQuestionRepository.getAll()).thenReturn(Collections.singleton(q));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(q);

    }

    @ParameterizedTest
    @MethodSource("question1")
    public void removeTest(Question question) {
        when(javaQuestionRepository.add(question)).thenReturn(true);
        javaQuestionService.add(question);

        when(javaQuestionRepository.remove(question)).thenReturn(true);
        javaQuestionService.remove(question);

        when(javaQuestionRepository.remove(question)).thenThrow(new QuestionNotFoundException());
        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> javaQuestionService.remove(question));
    }


    @ParameterizedTest
    @MethodSource("questions")
    public void removeQuestionTest(Set<Question> questions) {
        when(javaQuestionRepository.getAll()).thenReturn(questions);

        assertThat(javaQuestionService.getRandomQuestion()).isIn(javaQuestionService.getAll());
    }

    public static Stream<Arguments> question1() {
        return Stream.of(
                Arguments.of((new Question("Question", "Answer")))
        );
    }

    public static Stream<Arguments> question2() {
        return Stream.of(
                Arguments.of("Question", "Answer")
        );
    }

    public static Stream<Arguments> questions() {
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new Question("JavaQuestion1", "JavaAnswer1"),
                                new Question("JavaQuestion2", "JavaAnswer2"),
                                new Question("JavaQuestion3", "JavaAnswer3")
                        )
                )
        );
    }

}
