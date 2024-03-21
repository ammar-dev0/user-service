package com.devsinc.userservice.mappers;

import com.devsinc.userservice.dtos.request.CreateRoleRequest;
import com.devsinc.userservice.dtos.response.RoleResponse;
import com.devsinc.userservice.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    /**
     * Maps a Role object to a RoleResponse object.
     * @param role The Role object to be mapped.
     * @return The mapped RoleResponse object.
     */
    RoleResponse toResponse(Role role);

    /**
     * Maps a CreateRoleRequest object to a Role object.
     * @param roleRequest The CreateRoleRequest object to be mapped.
     * @return The mapped Role object.
     */
    Role toEntity(CreateRoleRequest roleRequest);

}
