package com.msa_service.user_service.service;

import com.msa_service.user_service.domain.User;
import com.msa_service.user_service.dto.UserRequestDto;
import com.msa_service.user_service.dto.UserResponseDto;
import com.msa_service.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserResponseDto createUser(UserRequestDto requestDto) {
    if (userRepository.existsByEmail(requestDto.getEmail())) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

    User saved = userRepository.save(User.builder()
            .name(requestDto.getName())
            .email(requestDto.getEmail())
            .password(encodedPassword)
            .build());

    return UserResponseDto.builder()
            .id(saved.getId())
            .name(saved.getName())
            .email(saved.getEmail())
            .build();
  }

  @Transactional(readOnly = true)
  public UserResponseDto getUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository.findAll().stream()
            .map(UserResponseDto::from)
            .collect(Collectors.toList());
  }
}