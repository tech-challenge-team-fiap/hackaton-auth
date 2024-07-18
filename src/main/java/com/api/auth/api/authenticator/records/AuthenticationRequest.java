package com.api.auth.api.authenticator.records;

public record AuthenticationRequest(
        String email,
        String password
) {
}