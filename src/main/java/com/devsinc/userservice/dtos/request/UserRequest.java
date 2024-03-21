package com.devsinc.userservice.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Username must be provided!")
    @Size(min = 5, max = 25, message = "Username must be between 5 to 25 characters long!")
    private String username;

    @NotBlank(message = "Email must be provided!")
    @Email(message = "Invalid email format!")
    private String email;

}
