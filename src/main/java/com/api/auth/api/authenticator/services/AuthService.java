package com.api.auth.api.authenticator.services;

import com.api.auth.api.authenticator.entities.Doctor;
import com.api.auth.api.authenticator.entities.Patient;
import com.api.auth.api.authenticator.entities.Token;
import com.api.auth.api.authenticator.entities.User;
import com.api.auth.api.authenticator.enums.RoleType;
import com.api.auth.api.authenticator.enums.TokenType;
import com.api.auth.api.authenticator.records.*;
import com.api.auth.api.authenticator.repositories.*;
import com.api.auth.api.authenticator.util.CPFValidator;
import com.api.auth.api.authenticator.util.CRMValidator;
import com.api.auth.api.authenticator.util.EmailValidator;
import com.api.auth.api.authenticator.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse loginPatient(AuthenticationPatientRequest request) {
        try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );
            var user = userRepository.findByEmail(request.email()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        }catch (Exception ex){
            throw new IllegalArgumentException("autenticacao com problema");
        }
    }

    public AuthenticationResponse loginDoctor(AuthenticationDoctorRequest request) {
        String email = doctorRepository.findByCrm(request.crm()).getEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );

        var doctor = doctorRepository.findByCrm(request.crm());
        var user = userRepository.findById(doctor.getId()).get();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }
}
