package com.devsinc.userservice.exceptions;

import com.devsinc.userservice.dtos.response.ApplicationResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ApplicationResponse<ExceptionMessage> notFoundHandler(NoSuchElementException e){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setErrors(List.of(e.getMessage()));
        return ApplicationResponse.<ExceptionMessage>builder()
                .exceptionMessages(exceptionMessage)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApplicationResponse<ExceptionMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        List<String> errors = new ArrayList<>();
        for (final FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        exceptionMessage.setErrors(errors);
        return ApplicationResponse.<ExceptionMessage>builder()
                .exceptionMessages(exceptionMessage)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ApplicationResponse<ExceptionMessage> handleTypeMismatch(TypeMismatchException e){
        String error = e.getValue() + " value for " + e.getPropertyName() + " should be of type " + e.getRequiredType();
        return ApplicationResponse.<ExceptionMessage>builder()
                .exceptionMessages(new ExceptionMessage(List.of(error)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApplicationResponse<ExceptionMessage> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e){
        String error = e.getName() + " should be of type " + Objects.requireNonNull(e.getRequiredType()).getName();
        return ApplicationResponse.<ExceptionMessage>builder()
                .exceptionMessages(new ExceptionMessage(List.of(error)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApplicationResponse<ExceptionMessage> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        ExceptionMessage exceptionMessage = new ExceptionMessage(List.of(builder.substring(0, builder.length() - 2)));
        return ApplicationResponse.<ExceptionMessage>builder()
                .exceptionMessages(exceptionMessage)
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApplicationResponse<ExceptionMessage> illegalArguments(IllegalArgumentException e){
        return ApplicationResponse.<ExceptionMessage>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionMessages(new ExceptionMessage(List.of(e.getMessage())))
                .build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ApplicationResponse<ExceptionMessage> invalidFormatException(InvalidFormatException e){
        return ApplicationResponse.<ExceptionMessage>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionMessages(new ExceptionMessage(List.of(e.getMessage())))
                .build();
    }

    @ExceptionHandler({ Exception.class })
    public ApplicationResponse<ExceptionMessage> handleAll(Exception ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(List.of(ex.getLocalizedMessage()));
        return ApplicationResponse.<ExceptionMessage>builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .exceptionMessages(exceptionMessage)
                .build();
    }
}
