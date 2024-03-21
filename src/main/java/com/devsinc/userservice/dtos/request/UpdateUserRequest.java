package com.devsinc.userservice.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest extends UserRequest{

    @NotEmpty(message = "User Id must be specified!")
    private String id;

}
