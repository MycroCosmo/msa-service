package com.msa_service.user_service.dto.request;

import com.msa_service.user_service.domain.UserRole;

public record UserRequest(String name, String email, String password, UserRole role) {
}