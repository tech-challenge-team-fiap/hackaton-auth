package com.api.auth.api.authenticator.config;

import com.api.auth.api.authenticator.entities.Role;
import com.api.auth.api.authenticator.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataBaseLoader {

    private RoleRepository roleRepository;

    public DataBaseLoader(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initializeDatabase() {
        if (!roleRepository.existsById(1L)) {
            roleRepository.save(new Role(1L, "DOCTOR"));
        }
        if (!roleRepository.existsById(2L)) {
            roleRepository.save(new Role(2L, "PATIENT"));
        }
    }

}
