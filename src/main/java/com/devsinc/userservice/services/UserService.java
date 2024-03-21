package com.devsinc.userservice.services;

import com.devsinc.userservice.dtos.request.CreateUserRequest;
import com.devsinc.userservice.dtos.request.UpdatePasswordRequest;
import com.devsinc.userservice.dtos.request.UpdateUserRequest;
import com.devsinc.userservice.dtos.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    List<UserResponse> getAllUsers();

    UserResponse findUserById(String id);

    UserResponse findUserByUsername(String username);

    UserResponse createUser(CreateUserRequest userRequest);

    UserResponse updateUser(UpdateUserRequest userRequest);

    UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    void deleteUser(String id);

}
