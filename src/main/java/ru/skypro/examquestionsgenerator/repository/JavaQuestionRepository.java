package ru.skypro.examquestionsgenerator.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.examquestionsgenerator.domain.Question;
import javax.annotation.PostConstruct;

@Repository
public class JavaQuestionRepository extends AbstractQuestionRepository {

    @PostConstruct
    public void init() {
        add(new Question("JavaQuestion1", "JavaAnswer1"));
        add(new Question("JavaQuestion2", "JavaAnswer2"));
        add(new Question("JavaQuestion3", "JavaAnswer3"));
        add(new Question("JavaQuestion4", "JavaAnswer4"));
    }
}
