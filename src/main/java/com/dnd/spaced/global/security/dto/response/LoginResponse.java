package com.dnd.spaced.global.security.dto.response;

public record LoginResponse(String accessToken, boolean isSignUp, String role, Long id) {
}
