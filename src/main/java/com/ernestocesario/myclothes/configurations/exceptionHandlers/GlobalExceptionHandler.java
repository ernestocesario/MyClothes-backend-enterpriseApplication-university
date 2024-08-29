package com.ernestocesario.myclothes.configurations.exceptionHandlers;

import com.ernestocesario.myclothes.configurations.mappers.appLogic.ExceptionMapper;
import com.ernestocesario.myclothes.exceptions.ApplicationException;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.persistance.DTOs.appLogic.ExceptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ExceptionMapper exceptionMapper;

    @Value("${testing}")
    private boolean testing;

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO onApplicationException(ApplicationException e) {
        return exceptionMapper.toExceptionDTO(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO onException(Exception e) {
        if (testing)
            return exceptionMapper.toExceptionDTO(e);

        return exceptionMapper.toExceptionDTO(new InternalServerErrorException());
    }
}