package com.api.auth.api.authenticator.services;

import com.api.auth.api.authenticator.entities.*;
import com.api.auth.api.authenticator.enums.RoleType;
import com.api.auth.api.authenticator.enums.TokenType;
import com.api.auth.api.authenticator.records.AuthenticationResponse;
import com.api.auth.api.authenticator.records.RegisterRequestDoctor;
import com.api.auth.api.authenticator.records.RegisterRequestPatient;
import com.api.auth.api.authenticator.repositories.*;
import com.api.auth.api.authenticator.util.CPFValidator;
import com.api.auth.api.authenticator.util.CRMValidator;
import com.api.auth.api.authenticator.util.EmailValidator;
import com.api.auth.api.authenticator.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );
//        var user = repository.findByEmail(request.email()).orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//
//        50(user);
//        saveUserToken(user, jwtToken);
//
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse registerDoctor(RegisterRequestDoctor request) {

        PasswordValidator.validatePassword(request.password());
        EmailValidator.isValidEmail(request.email());
        CRMValidator.validateCRM(request.crm());

        Doctor doctor = doctorRepository.findByCrm(request.crm());
        if (doctor != null) {
            throw new IllegalArgumentException("CRM já está em uso");
        }

        Doctor newDoctor = new Doctor();
        newDoctor.setName(request.name());
        newDoctor.setCrm(request.crm());
        newDoctor.setEmail(request.email());
        newDoctor.setSpecialty(request.specialty());
        newDoctor.setPassword(passwordEncoder.encode(request.password()));

        var role = roleRepository.findByRole(RoleType.DOCTOR.name());
        newDoctor.setRole(role.get());

        //Save Register about New Doctor
        doctorRepository.save(newDoctor);

        // Create JWT Token for access
        var jwtToken = jwtService.generateToken(newDoctor);

        var user = userRepository.findById(newDoctor.getId()).get();

        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(token);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse registerPatient(RegisterRequestPatient request) {

        PasswordValidator.validatePassword(request.password());
        CPFValidator.isValidCPF(request.cpf());
        EmailValidator.isValidEmail(request.email());

        Patient patient = patientRepository.findByCpf(request.cpf());
        if (patient != null) {
            throw new IllegalArgumentException("CPF já está em uso");
        }

        Patient newPatient = new Patient();
        newPatient.setName(request.name());
        newPatient.setCpf(request.cpf());
        newPatient.setEmail(request.email());
        newPatient.setPassword(passwordEncoder.encode(request.password()));
        newPatient.setType(RoleType.PATIENT);

        var role = roleRepository.findByRole(RoleType.PATIENT.name());
        newPatient.setRole(role.get());

        //Save Register about New Patient
        patientRepository.save(newPatient);

        // Create JWT Token for access
        var jwtToken = jwtService.generateToken(newPatient);

        var user = userRepository.findById(newPatient.getId()).get();

        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
