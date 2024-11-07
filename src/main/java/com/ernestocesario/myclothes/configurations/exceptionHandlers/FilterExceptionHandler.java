package com.ernestocesario.myclothes.configurations.exceptionHandlers;

import com.ernestocesario.myclothes.configurations.mappers.appLogic.ExceptionMapper;
import com.ernestocesario.myclothes.exceptions.auth.InvalidAccessTokenException;
import com.ernestocesario.myclothes.persistance.DTOs.appLogic.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandler implements Filter {
    private final ExceptionMapper exceptionMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException((HttpServletResponse) response, e);
        }
    }


    //private function
    private void handleException(HttpServletResponse response, Exception e) {
        ExceptionDTO exceptionDTO = exceptionMapper.toExceptionDTO(e);

        if (e instanceof InvalidAccessTokenException)
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        else
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(exceptionDTO));
        }
        catch (IOException ignored) {
        }
    }
}
