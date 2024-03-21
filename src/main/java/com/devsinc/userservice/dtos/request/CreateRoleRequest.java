package com.devsinc.userservice.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoleRequest {

    /**
     * The name of the role.
     */
    private String roleName;

}
