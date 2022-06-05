package net.bos.om.api.controller;

import net.bos.om.api.exception.BookingServiceException;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Locale;

@ControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody InvocationResponse<String> handleConstraintViolationException(ConstraintViolationException e) {
        InvocationResponse<String> response = new InvocationResponse<>();
        response.setErrorMessages(new ArrayList<>());
        e.getConstraintViolations().forEach(c -> response.getErrorMessages().add(c.getMessage()));
        return response;
    }

    @ExceptionHandler(BookingServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody InvocationResponse<String> handleBookingServiceException(BookingServiceException e, Locale locale) {
        InvocationResponse<String> response = new InvocationResponse<>();
        response.setErrorMessages(new ArrayList<>());
        String errorMessage = messageSource.getMessage(e.getMessage(), null, locale);
        response.getErrorMessages().add(errorMessage);
        return response;
    }
}
