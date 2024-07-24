package com.api.auth.api.authenticator.controller;

import com.api.auth.api.authenticator.exceptions.ErrorResponse;
import com.api.auth.api.authenticator.records.AuthenticationDoctorRequest;
import com.api.auth.api.authenticator.records.AuthenticationPatientRequest;
import com.api.auth.api.authenticator.records.RegisterRequestDoctor;
import com.api.auth.api.authenticator.records.RegisterRequestPatient;
import com.api.auth.api.authenticator.services.AuthService;
import com.api.auth.api.authenticator.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/doctor")
    public ResponseEntity<?> loginDoctor(@RequestBody AuthenticationDoctorRequest request) {
        try{
            return ResponseEntity.ok(service.loginDoctor(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error ao realizar login:", e.getMessage()));
        }
    }

    @PostMapping("/patient")
    public ResponseEntity<?> loginPatient(@RequestBody AuthenticationPatientRequest request){
        try{
            return ResponseEntity.ok(service.loginPatient(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error ao realizar login:", e.getMessage()));
        }
    }
}