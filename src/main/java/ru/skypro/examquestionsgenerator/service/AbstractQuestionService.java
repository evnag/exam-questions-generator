package ru.skypro.examquestionsgenerator.service;

import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.QuestionIsAlreadyAddedException;
import ru.skypro.examquestionsgenerator.exception.QuestionNotFoundException;
import ru.skypro.examquestionsgenerator.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AbstractQuestionService implements QuestionService {

    private final QuestionRepository repository;
    private final Random random = new Random();

    public AbstractQuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!repository.add(question)) {
            throw new QuestionIsAlreadyAddedException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!repository.remove(question)) {
            throw new QuestionIsAlreadyAddedException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return repository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        ArrayList<Question> listOfQuestion = new ArrayList<>(repository.getAll());
        if (!listOfQuestion.isEmpty()) {
            return listOfQuestion.get(random.nextInt(listOfQuestion.size()));
        } else {
            throw new QuestionNotFoundException();
        }
    }
}
