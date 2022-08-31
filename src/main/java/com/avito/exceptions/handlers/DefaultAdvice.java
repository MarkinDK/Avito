package com.avito.exceptions.handlers;

import com.avito.exceptions.ChatCreatingException;
import com.avito.exceptions.ElementAlreadyExists;
import com.avito.exceptions.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> HandleNoSuchElementException(NoSuchElementException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> HandleElementAlreadyExists(ElementAlreadyExists exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ChatCreatingException.class)
    public ResponseEntity<ExceptionResponse> HandleChatCreatingException(ChatCreatingException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
