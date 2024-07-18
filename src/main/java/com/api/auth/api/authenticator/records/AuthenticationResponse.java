package com.api.auth.api.authenticator.records;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
