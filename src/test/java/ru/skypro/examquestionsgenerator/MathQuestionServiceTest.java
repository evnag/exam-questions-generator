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
import ru.skypro.examquestionsgenerator.repository.MathQuestionRepository;
import ru.skypro.examquestionsgenerator.service.MathQuestionService;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MathQuestionServiceTest {

    @Mock
    private MathQuestionRepository mathQuestionRepository;

    @InjectMocks
    private MathQuestionService mathQuestionService;

    @ParameterizedTest
    @MethodSource("question1")
    public void addTest1(Question question) {
        when(mathQuestionRepository.add(question)).thenReturn(true);
        mathQuestionService.add(question);

        when(mathQuestionRepository.add(question)).thenThrow(new QuestionIsAlreadyAddedException());
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> mathQuestionService.add(question));

        when(mathQuestionRepository.getAll()).thenReturn(Collections.singleton(question));
        assertThat(mathQuestionService.getAll()).containsExactlyInAnyOrder(question);
    }

    @ParameterizedTest
    @MethodSource("question2")
    public void addTest2(String question, String answer) {
        Question q = new Question(question, answer);
        when(mathQuestionRepository.add(new Question(question, answer))).thenReturn(true);
        mathQuestionService.add(question, answer);

        when(mathQuestionRepository.add(new Question(question, answer))).thenThrow(new QuestionIsAlreadyAddedException());
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> mathQuestionService.add(question, answer));

        when(mathQuestionRepository.getAll()).thenReturn(Collections.singleton(q));
        assertThat(mathQuestionService.getAll()).containsExactlyInAnyOrder(q);

    }

    @ParameterizedTest
    @MethodSource("question1")
    public void removeTest(Question question) {
        when(mathQuestionRepository.add(question)).thenReturn(true);
        mathQuestionService.add(question);

        when(mathQuestionRepository.remove(question)).thenReturn(true);
        mathQuestionService.remove(question);

        when(mathQuestionRepository.remove(question)).thenThrow(new QuestionNotFoundException());
        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> mathQuestionService.remove(question));
    }


    @ParameterizedTest
    @MethodSource("questions")
    public void removeQuestionTest(Set<Question> questions) {
        when(mathQuestionRepository.getAll()).thenReturn(questions);

        assertThat(mathQuestionService.getRandomQuestion()).isIn(mathQuestionService.getAll());
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
                                new Question("MathQuestion1", "MathAnswer1"),
                                new Question("MathQuestion2", "MathAnswer2"),
                                new Question("MathQuestion3", "MathAnswer3")
                        )
                )
        );
    }
}
