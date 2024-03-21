package com.devsinc.userservice.dtos.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@ToString
public class ApplicationResponse<T> {

    private T exceptionMessages;

    private HttpStatus httpStatus;

    private T data;

}
p