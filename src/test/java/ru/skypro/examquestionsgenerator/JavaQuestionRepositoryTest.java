package ru.skypro.examquestionsgenerator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.repository.JavaQuestionRepository;
import ru.skypro.examquestionsgenerator.repository.QuestionRepository;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaQuestionRepositoryTest {

    private final QuestionRepository questionRepository = new JavaQuestionRepository();

    @ParameterizedTest
    @MethodSource("question1")
    public void add1Test(Question question) {
        questionRepository.add(question);
        assertThat(questionRepository.getAll()).containsExactlyInAnyOrder(question);
    }

    @ParameterizedTest
    @MethodSource("question2")
    public void add2Test(String question, String answer) {
        questionRepository.add(new Question(question, answer));
        assertThat(questionRepository.getAll()).containsExactlyInAnyOrder(new Question(question, answer));
    }

    @ParameterizedTest
    @MethodSource("question1")
    public void removeTest(Question question) {
        questionRepository.add(question);
        questionRepository.remove(question);
        assertThat(questionRepository.getAll()).isEmpty();
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


}
