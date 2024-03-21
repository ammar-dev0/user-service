package com.devsinc.userservice.services.impl;

import com.devsinc.userservice.dtos.request.CreateUserRequest;
import com.devsinc.userservice.dtos.request.UpdatePasswordRequest;
import com.devsinc.userservice.dtos.request.UpdateUserRequest;
import com.devsinc.userservice.dtos.response.UserResponse;
import com.devsinc.userservice.mappers.UserMapper;
import com.devsinc.userservice.models.Role;
import com.devsinc.userservice.models.User;
import com.devsinc.userservice.repositories.RoleRepository;
import com.devsinc.userservice.repositories.UserRepository;
import com.devsinc.userservice.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all users.
     * @return List of UserResponse objects representing all users.
     */
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.MAPPER::toResponse).toList();
    }

    /**
     * Find a user by ID.
     * @param id The ID of the user to find.
     * @return The UserResponse object representing the found user.
     * @throws NoSuchElementException if no user with the given ID is found.
     */
    @Override
    public UserResponse findUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Unable to find user with id: " + id)
        );
        return UserMapper.MAPPER.toResponse(user);
    }

    /**
     * Find a user by username.
     * @param username The username of the user to find.
     * @return The UserResponse object representing the found user.
     * @throws NoSuchElementException if no user with the given username is found.
     */
    @Override
    public UserResponse findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Unable to find user with username: " + username)
        );
        return UserMapper.MAPPER.toResponse(user);
    }

    /**
     * Create a new user.
     * @param userRequest The CreateUserRequest object containing the user details.
     * @return The UserResponse object representing the created user.
     * @throws IllegalStateException if a user with the given username or email already exists.
     * @throws NoSuchElementException if a role with a given roleName does not exist.
     */
    @Override
    public UserResponse createUser(CreateUserRequest userRequest) {
        User userToBeCreated = UserMapper.MAPPER.toModel(userRequest);
        boolean userExists = userRepository.findByUsername(userToBeCreated.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("User with username: " + userToBeCreated.getEmail() + " already registered");
        }
        userExists = userRepository.findByEmail(userToBeCreated.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("User with email: " + userToBeCreated.getEmail() + " already registered");
        }
        userToBeCreated.setId(UUID.randomUUID().toString());
        userToBeCreated.setPassword(passwordEncoder.encode(userToBeCreated.getPassword()));
        List<Role> roles = userRequest.getRoleNames().stream().map(
                roleName -> roleRepository.findByRoleName(roleName).orElseThrow(
                        () -> new NoSuchElementException("No role with roleName: " + roleName + " exists")
                )
        ).toList();
        userToBeCreated.setRoles(roles);
        return UserMapper.MAPPER.toResponse(userRepository.save(userToBeCreated));
    }

    /**
     * Update an existing user.
     * @param userRequest The UpdateUserRequest object containing the updated user details.
     * @return The UserResponse object representing the updated user.
     * @throws NoSuchElementException if no user with the given ID is found.
     */
    @Override
    public UserResponse updateUser(UpdateUserRequest userRequest) {
        User userToBeUpdated = userRepository.findById(userRequest.getId()).orElseThrow(
                () -> new NoSuchElementException("Unable to find user with id: " + userRequest.getId())
        );
        User user = UserMapper.MAPPER.toModel(userRequest, userToBeUpdated);
        return UserMapper.MAPPER.toResponse(userRepository.save(user));
    }

    /**
     * Update the password of a user.
     * @param updatePasswordRequest The UpdatePasswordRequest object containing the user ID and new password.
     * @return The UserResponse object representing the user with the updated password.
     * @throws NoSuchElementException if no user with the given ID is found.
     * @throws RuntimeException if the provided current password is incorrect.
     */
    @Override
    public UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User userToBeUpdated = userRepository.findById(updatePasswordRequest.getId()).orElseThrow(
                () -> new NoSuchElementException("Unable to find user with id: " + updatePasswordRequest.getId())
        );
        if (userToBeUpdated.getPassword().equals(updatePasswordRequest.getCurrentPassword())){
            userToBeUpdated.setPassword(userToBeUpdated.getPassword());
            userRepository.save(userToBeUpdated);
        }
        throw new RuntimeException("Incorrect Password");
    }

    /**
     * Delete a user by ID.
     * @param id The ID of the user to delete.
     * @throws NoSuchElementException if no user with the given ID is found.
     */
    @Override
    public void deleteUser(String id) {
        User userToBeDeleted = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Unable to find user with id: " + id)
        );
        userRepository.delete(userToBeDeleted);
    }

    /**
     * Load a user by username.
     * @param username The username of the user to load.
     * @return A UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException if no user with the given username is found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + username + " not found!")
        );
    }
}
