package ru.skypro.examquestionsgenerator.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.examquestionsgenerator.service.QuestionService;


@RestController
@RequestMapping("/math")
public class MathQuestionController extends JavaQuestionController{


    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService questionService) {
        super(questionService);
    }
}
