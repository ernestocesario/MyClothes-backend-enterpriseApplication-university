package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.ExceptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {
    default ExceptionDTO toExceptionDTO(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setExceptionType(e.getClass().getSimpleName());
        return exceptionDTO;
    }
}
