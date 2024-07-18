package com.api.auth.api.authenticator.repositories;

import com.api.auth.api.authenticator.entities.Doctor;
import com.api.auth.api.authenticator.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByCpf(String cpf);

}
