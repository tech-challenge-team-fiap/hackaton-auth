package com.api.auth.api.authenticator.records;

public record RegisterRequestPatient(
        String name,
        String cpf,
        String email,
        String password
) {
}
