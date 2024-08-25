package com.ernestocesario.myclothes.configurations.mappers.appLogic;

import com.ernestocesario.myclothes.persistance.DTOs.appLogic.ExceptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {
    default ExceptionDTO toExceptionDTO(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setExceptionType(e.getClass().getSimpleName());
        return exceptionDTO;
    }
}
