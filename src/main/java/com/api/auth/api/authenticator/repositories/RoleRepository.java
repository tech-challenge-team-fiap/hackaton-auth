package com.api.auth.api.authenticator.repositories;

import com.api.auth.api.authenticator.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String user);
}
