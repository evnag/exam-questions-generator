package ru.skypro.examquestionsgenerator.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skypro.examquestionsgenerator.repository.QuestionRepository;

@Service
public class JavaQuestionService extends AbstractQuestionService {

    public JavaQuestionService(@Qualifier("javaQuestionRepository") QuestionRepository repository) {
        super(repository);
    }

}
