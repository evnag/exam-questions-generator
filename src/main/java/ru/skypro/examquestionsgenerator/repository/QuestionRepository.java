package ru.skypro.examquestionsgenerator.repository;

import ru.skypro.examquestionsgenerator.domain.Question;

import java.util.Collection;

public interface QuestionRepository {

    boolean add(Question question);

    boolean remove(Question question);

    Collection<Question> getAll();
}
