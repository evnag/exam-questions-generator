package ru.skypro.examquestionsgenerator.repository;

import ru.skypro.examquestionsgenerator.domain.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractQuestionRepository implements QuestionRepository {

    private final Set<Question> questions = new HashSet<>();

    public abstract void init();

    @Override
    public boolean add(Question question) {
        return questions.add(question);
    }

    @Override
    public boolean remove(Question question) {
        return questions.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }
}

