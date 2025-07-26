package com.msa_service.user_service.dto;

import com.msa_service.user_service.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
  private Long id;
  private String name;
  private String email;

  public static UserResponseDto from(User user) {
    return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();
  }
}
