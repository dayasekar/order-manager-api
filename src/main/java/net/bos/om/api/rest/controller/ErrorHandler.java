package net.bos.om.api.rest.controller;

import net.bos.om.api.vo.InvocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody InvocationResponse<String> handleConstraintViolationException(ConstraintViolationException e) {
        InvocationResponse<String> response = new InvocationResponse<>();
        response.setErrorMessages(new ArrayList<>());
        e.getConstraintViolations().stream().forEach(c -> response.getErrorMessages().add(c.getMessage()));
        return response;
    }
}
