package com.devsinc.userservice.controllers;

import com.devsinc.userservice.dtos.request.CreateRoleRequest;
import com.devsinc.userservice.dtos.response.ApplicationResponse;
import com.devsinc.userservice.dtos.response.RoleResponse;
import com.devsinc.userservice.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller class for managing roles.
 * Exposes REST endpoints for role operations.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructs a RoleController with the specified RoleService.
     * @param roleService the service for roles
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Retrieves all roles.
     * @return an ApplicationResponse containing the HTTP status and a list of role responses
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ApplicationResponse<List<RoleResponse>> getAll(){
        return ApplicationResponse.<List<RoleResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .data(roleService.getAllRoles())
                .build();
    }

    /**
     * Retrieves a role by its ID.
     * @param id the ID of the role to retrieve
     * @return an ApplicationResponse containing the HTTP status and the role response
     * @throws NoSuchElementException if the role with the specified ID is not found
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find-by-id/{id}")
    public ApplicationResponse<RoleResponse> getAll(@PathVariable String id){
        return ApplicationResponse.<RoleResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(roleService.getRole(id))
                .build();
    }

    /**
     * Creates a new role.
     * @param roleRequest the request object containing the role information to be created
     * @return an ApplicationResponse containing the HTTP status and the created role response
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ApplicationResponse<RoleResponse> create(@RequestBody @Valid CreateRoleRequest roleRequest){
        return ApplicationResponse.<RoleResponse>builder()
                .httpStatus(HttpStatus.CREATED)
                .data(roleService.saveRole(roleRequest))
                .build();
    }
}
