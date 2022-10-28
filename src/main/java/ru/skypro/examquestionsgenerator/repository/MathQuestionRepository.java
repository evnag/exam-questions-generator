package ru.skypro.examquestionsgenerator.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.examquestionsgenerator.domain.Question;

import javax.annotation.PostConstruct;

@Repository
public class MathQuestionRepository extends AbstractQuestionRepository{

    @PostConstruct
    public void init() {
        add(new Question("MathQuestion1", "MathAnswer1"));
        add(new Question("MathQuestion2", "MathAnswer2"));
        add(new Question("MathQuestion3", "MathAnswer3"));
        add(new Question("MathQuestion4", "MathAnswer4"));
    }
}
