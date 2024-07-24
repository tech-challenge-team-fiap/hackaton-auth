package com.api.auth.api.authenticator.records;

public record AuthenticationDoctorRequest(
        String crm,
        String password
) {
}