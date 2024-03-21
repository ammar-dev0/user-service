package com.devsinc.userservice.services.impl;

import com.devsinc.userservice.dtos.request.CreateRoleRequest;
import com.devsinc.userservice.dtos.response.RoleResponse;
import com.devsinc.userservice.mappers.RoleMapper;
import com.devsinc.userservice.models.Role;
import com.devsinc.userservice.repositories.RoleRepository;
import com.devsinc.userservice.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Implementation of the RoleService interface.
 * Provides methods for managing roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructs a RoleServiceImpl with the specified RoleRepository.
     * @param roleRepository the repository for roles
     */
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Saves a new role.
     * @param roleRequest the request object containing the role information to be saved
     * @return the created role response
     */
    @Override
    public RoleResponse saveRole(CreateRoleRequest roleRequest) {
        Role roleToBeSaved = RoleMapper.MAPPER.toEntity(roleRequest);
        roleToBeSaved.setId(UUID.randomUUID().toString());
        return RoleMapper.MAPPER.toResponse(roleRepository.save(roleToBeSaved));
    }

    /**
     * Retrieves a role by its ID.
     * @param id the ID of the role to retrieve
     * @return the retrieved role response
     * @throws NoSuchElementException if the role with the specified ID is not found
     */
    @Override
    public RoleResponse getRole(String id) {
        Role byId = roleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Unable to find role with id: " + id)
        );
        return RoleMapper.MAPPER.toResponse(byId);
    }

    /**
     * Retrieves all roles.
     * @return a list of all role responses
     */
    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(RoleMapper.MAPPER::toResponse).toList();
    }
}
