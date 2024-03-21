package com.devsinc.userservice.controllers;

import com.devsinc.userservice.dtos.request.CreateUserRequest;
import com.devsinc.userservice.dtos.request.UpdatePasswordRequest;
import com.devsinc.userservice.dtos.request.UpdateUserRequest;
import com.devsinc.userservice.dtos.response.ApplicationResponse;
import com.devsinc.userservice.dtos.response.UserResponse;
import com.devsinc.userservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     * @return an ApplicationResponse containing the HTTP status and a list of user responses
     */
    @GetMapping("/getAll")
    public ApplicationResponse<List<UserResponse>> getAllUsers(){
        List<UserResponse> userResponses = userService.getAllUsers();
        return ApplicationResponse.<List<UserResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponses)
                .build();
    }

    /**
     * Retrieves a user by their ID.
     * @param id the ID of the user to retrieve
     * @return an ApplicationResponse containing the HTTP status and the user response
     * @throws java.util.NoSuchElementException if the user with the specified ID is not found
     */
    @GetMapping("/get/{id}")
    public ApplicationResponse<UserResponse> findUserById(@PathVariable String id){
        UserResponse userResponse = userService.findUserById(id);
        return ApplicationResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    /**
     * Retrieves a user by their username.
     * @param username the username of the user to retrieve
     * @return an ApplicationResponse containing the HTTP status and the user response
     * @throws java.util.NoSuchElementException if the user with the specified username is not found
     */
    @GetMapping("/get-by-username/{username}")
    public ApplicationResponse<UserResponse> findByUsername(@PathVariable String username){
        UserResponse userResponse = userService.findUserByUsername(username);
        return ApplicationResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    /**
     * Registers a new user.
     * @param userRequest the request object containing the user information to be registered
     * @return an ApplicationResponse containing the HTTP status and the created user response
     * @throws org.springframework.web.bind.MethodArgumentNotValidException if the user request fails validation
     */
    @PostMapping("/register")
    public ApplicationResponse<UserResponse> create(@RequestBody @Valid CreateUserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return ApplicationResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    /**
     * Updates a user.
     * @param userRequest the request object containing the updated user information
     * @return an ApplicationResponse containing the HTTP status and the updated user response
     * @throws org.springframework.web.bind.MethodArgumentNotValidException if the user request fails validation
     */
    @PostMapping("/update")
    public ApplicationResponse<UserResponse> update(@RequestBody @Valid UpdateUserRequest userRequest){
        UserResponse userResponse = userService.updateUser(userRequest);
        return ApplicationResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    /**
     * Updates a user's password.
     * @param userRequest the request object containing the updated password information
     * @return an ApplicationResponse containing the HTTP status and the updated user response
     * @throws org.springframework.web.bind.MethodArgumentNotValidException if the user request fails validation
     */
    @PostMapping("/update-password")
    public ApplicationResponse<UserResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest userRequest){
        UserResponse userResponse = userService.updatePassword(userRequest);
        return ApplicationResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete
     * @return an ApplicationResponse containing the HTTP status
     */
    @DeleteMapping("/delete/{id}")
    public ApplicationResponse<Object> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApplicationResponse.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
