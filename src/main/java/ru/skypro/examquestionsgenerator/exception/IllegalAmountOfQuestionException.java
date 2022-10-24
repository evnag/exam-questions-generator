package ru.skypro.examquestionsgenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalAmountOfQuestionException extends RuntimeException {
}