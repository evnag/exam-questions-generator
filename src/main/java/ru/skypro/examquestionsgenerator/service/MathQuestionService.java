package ru.skypro.examquestionsgenerator.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skypro.examquestionsgenerator.repository.QuestionRepository;

@Service
public class MathQuestionService extends AbstractQuestionService {

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository repository) {
        super(repository);
    }
}
