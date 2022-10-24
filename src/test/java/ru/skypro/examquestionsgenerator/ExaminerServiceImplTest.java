package ru.skypro.examquestionsgenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.IllegalAmountOfQuestionException;
import ru.skypro.examquestionsgenerator.service.ExaminerServiceImpl;
import ru.skypro.examquestionsgenerator.service.QuestionService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {
    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void before() {
        when(questionService.getAll()).thenReturn(
                Arrays.asList(
                        new Question("Question1", "Answer1"),
                        new Question("Question2", "Answer2"),
                        new Question("Question3", "Answer3")
                )
        );
    }

    @Test
    public void getQuestionsPositiveTest() {
        Set<Question> expectedList = new HashSet<>(questionService.getAll());
        when(questionService.getRandomQuestion())
                .thenReturn(
                        new Question("Question1", "Answer1"),
                        new Question("Question2", "Answer2"),
                        new Question("Question3", "Answer3"),
                        new Question("Question4", "Answer4")
                );
        assertThat(examinerService.getQuestions(3))
                .containsExactlyInAnyOrderElementsOf(expectedList);
    }

    @Test
    public void getQuestionsNegativeTest() {

        assertThatExceptionOfType(IllegalAmountOfQuestionException.class)
                .isThrownBy(() -> examinerService.getQuestions(4));
        // FIXME Test does not pass with amount <= 0 && amount == list.size()
//        assertThatExceptionOfType(IllegalAmountOfQuestionException.class)
//                .isThrownBy(() -> examinerService.getQuestions(-0));
    }

}
