package com.p5.penta1.api.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RolesEnum rolesEnum);
}
