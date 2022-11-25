package ru.skypro.examquestionsgenerator.service;

import ru.skypro.examquestionsgenerator.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}
