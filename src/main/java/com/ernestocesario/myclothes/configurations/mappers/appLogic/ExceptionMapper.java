package com.ernestocesario.myclothes.configurations.mappers.appLogic;

import com.ernestocesario.myclothes.exceptions.ApplicationException;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.persistance.DTOs.appLogic.ExceptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {
    default ExceptionDTO toExceptionDTO(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();

        if (e instanceof ApplicationException)
            exceptionDTO.setExceptionType(e.getClass().getSimpleName());
        else
            exceptionDTO.setExceptionType(InternalServerErrorException.class.getSimpleName());

        return exceptionDTO;
    }
}
