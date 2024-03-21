package com.devsinc.userservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest extends UserRequest{

    /**
     * The password for the user.
     */
    @NotBlank(message = "Password must be provided!")
    private String password;

    /**
     * The list of role names associated with the user.
     */
    @NotEmpty(message = "Role Name must be provided!")
    private List<String> roleNames;

}
