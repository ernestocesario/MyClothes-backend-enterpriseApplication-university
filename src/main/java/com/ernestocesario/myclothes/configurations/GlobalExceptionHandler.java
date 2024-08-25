package com.ernestocesario.myclothes.configurations;

import com.ernestocesario.myclothes.configurations.mappers.appLogic.ExceptionMapper;
import com.ernestocesario.myclothes.persistance.DTOs.appLogic.ExceptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ExceptionMapper exceptionMapper;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO onException(Exception e) {
        return exceptionMapper.toExceptionDTO(e);
    }
}
