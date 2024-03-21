package com.devsinc.userservice.repositories;

import com.devsinc.userservice.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByRoleName(String roleName);

}
