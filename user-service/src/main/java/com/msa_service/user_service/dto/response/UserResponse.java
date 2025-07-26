package com.msa_service.user_service.dto.response;

import com.msa_service.user_service.domain.User;

public record UserResponse(Long id, String name, String email) {

  public UserResponse(User user){
    this(user.getId(), user.getName(), user.getEmail());
  }

  public static UserResponse from(User user) {
    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }
}
