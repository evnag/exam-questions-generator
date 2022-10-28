package ru.skypro.examquestionsgenerator.service;

import org.springframework.stereotype.Service;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.IllegalAmountOfQuestionException;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final Random random;

    private final QuestionService javaQuestionRepository;
    private final QuestionService mathQuestionRepository;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionRepository,
                               MathQuestionService mathQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
        this.mathQuestionRepository = mathQuestionRepository;
        this.random = new Random();
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        List<QuestionService> list = List.of(javaQuestionRepository, mathQuestionRepository);

        int totalQuestions = list.stream()
                .map(QuestionService::getAll)
                .mapToInt(Collection::size)
                .sum();

        if (amount <= 0 || amount > totalQuestions) {
            throw new IllegalAmountOfQuestionException();
        }
        Set<Question> setOfQuestions = new HashSet<>();
        while (setOfQuestions.size() < amount) {
            setOfQuestions.add(list.get(random.nextInt(list.size())).getRandomQuestion());
        }
        return setOfQuestions;
    }
}
