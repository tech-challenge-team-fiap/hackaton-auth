package com.api.auth.api.authenticator.records;

public record AuthenticationPatientRequest(
        String email,
        String password
) {
}