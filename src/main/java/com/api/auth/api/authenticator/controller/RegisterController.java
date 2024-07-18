package com.api.auth.api.authenticator.controller;

import com.api.auth.api.authenticator.exceptions.ErrorResponse;
import com.api.auth.api.authenticator.records.AuthenticationResponse;
import com.api.auth.api.authenticator.records.RegisterRequestDoctor;
import com.api.auth.api.authenticator.records.RegisterRequestPatient;
import com.api.auth.api.authenticator.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService service;

    @PostMapping("/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequestDoctor request) {
        try{
            return ResponseEntity.ok(service.registerDoctor(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error ao registrar médico", e.getMessage()));
        }
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatient(@RequestBody RegisterRequestPatient request){
        try{
            return ResponseEntity.ok(service.registerPatient(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error ao registrar patient", e.getMessage()));
        }
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
}