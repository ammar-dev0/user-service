package com.devsinc.userservice.mappers;

import com.devsinc.userservice.dtos.request.CreateUserRequest;
import com.devsinc.userservice.dtos.request.UpdateUserRequest;
import com.devsinc.userservice.dtos.response.UserResponse;
import com.devsinc.userservice.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a User object to a UserResponse object.
     * @param user The User object to be mapped.
     * @return The mapped UserResponse object.
     */
    UserResponse toResponse(User user);

    /**
     * Maps a CreateUserRequest object to a User object.
     * @param userRequest The CreateUserRequest object to be mapped.
     * @return The mapped User object.
     */
    User toModel(CreateUserRequest userRequest);

    /**
     * Maps an UpdateUserRequest object to an existing User object.
     * @param userRequest The UpdateUserRequest object to be mapped.
     * @param user        The existing User object to be updated.
     * @return The updated User object.
     */
    User toModel(UpdateUserRequest userRequest, @MappingTarget User user);

}
