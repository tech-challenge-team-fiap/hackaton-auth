package com.api.auth.api.authenticator.records;

public record RegisterRequestDoctor(
        String name,
        String crm,
        String email,
        String specialty,
        String password
) {
}
