package com.devsinc.userservice.services;

import com.devsinc.userservice.dtos.request.CreateRoleRequest;
import com.devsinc.userservice.dtos.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse saveRole(CreateRoleRequest roleRequest);

    RoleResponse getRole(String id);

    List<RoleResponse> getAllRoles();

}
