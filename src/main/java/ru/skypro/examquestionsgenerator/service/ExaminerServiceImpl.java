package ru.skypro.examquestionsgenerator.service;

import org.springframework.stereotype.Service;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.IllegalAmountOfQuestionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > 0 && amount <= questionService.getAll().size()) {
            Set<Question> setOfQuestions = new HashSet<>();
            while (setOfQuestions.size() < amount) {
                setOfQuestions.add(questionService.getRandomQuestion());
            }
            return setOfQuestions;
        } else {
            throw new IllegalAmountOfQuestionException();
        }
    }
}
