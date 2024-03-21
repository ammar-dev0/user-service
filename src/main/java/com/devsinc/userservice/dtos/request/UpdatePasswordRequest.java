package com.devsinc.userservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotEmpty(message = "User Id must be specified!")
    private String id;

    @NotBlank(message = "Current Password must be provided!")
    private String currentPassword;

    @NotBlank(message = "New Password must be provided!")
    private String newPassword;

}
