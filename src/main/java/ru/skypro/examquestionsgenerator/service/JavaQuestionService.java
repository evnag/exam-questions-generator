package ru.skypro.examquestionsgenerator.service;

import org.springframework.stereotype.Service;
import ru.skypro.examquestionsgenerator.domain.Question;
import ru.skypro.examquestionsgenerator.exception.QuestionIsAlreadyAddedException;
import ru.skypro.examquestionsgenerator.exception.QuestionNotFoundException;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> javaQuestions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!javaQuestions.contains(question)) {
            javaQuestions.add(question);
        } else {
            throw new QuestionIsAlreadyAddedException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        Question removedQuestion = javaQuestions.stream()
                .filter(q -> q.getQuestion().equals(question.getQuestion()) && q.getAnswer().equals(question.getAnswer()))
                .findFirst()
                .orElseThrow(QuestionNotFoundException::new);
        javaQuestions.remove(removedQuestion);
        return removedQuestion;
    }

    @Override
    public Collection<Question> getAll() {
        return new ArrayList<>(javaQuestions);
    }

    @Override
    public Question getRandomQuestion() {
        ArrayList<Question> listOfQuestion = new ArrayList<>(javaQuestions);
        if (!listOfQuestion.isEmpty()) {

//            randomQuestion = javaQuestions.stream()
//                    .skip(random.nextInt(javaQuestions.size()))
//                    .findFirst()
//                    .orElseThrow(QuestionNotFoundException::new);
            return listOfQuestion.get(random.nextInt(listOfQuestion.size()));
        } else {
            throw new QuestionNotFoundException();
        }
    }
}
