package com.api.auth.api.authenticator.repositories;

import com.api.auth.api.authenticator.entities.Doctor;
import com.api.auth.api.authenticator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByCrm(String crm);

}
