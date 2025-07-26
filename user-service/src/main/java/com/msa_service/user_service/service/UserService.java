package com.msa_service.user_service.service;

import com.msa_service.user_service.domain.User;
import com.msa_service.user_service.dto.request.UserRequest;
import com.msa_service.user_service.dto.request.UserUpdateDto;
import com.msa_service.user_service.dto.response.UserResponse;
import com.msa_service.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

  public UserResponse createUser(UserRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    String encodedPassword = passwordEncoder.encode(request.password());

    User saved = userRepository.save(User.builder()
            .name(request.name())
            .email(request.email())
            .password(encodedPassword)
            .role(request.role())
            .build());

    return new UserResponse(saved.getId(), saved.getName(), saved.getEmail());
  }

  @Transactional(readOnly = true)
  public UserResponse getUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public List<UserResponse> getAllUsers() {
    return userRepository.findAll().stream()
            .map(UserResponse::from)
            .collect(Collectors.toList());
  }

  public UserResponse updateUser(Long id, UserUpdateDto dto) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

    // 변경 감지 (Dirty Checking)
    if (dto.name() != null) {
      user.updateName(dto.name());
    }
    if (dto.email() != null) {
      user.updateEmail(dto.email());
    }

    // JPA는 @Transactional 내부에서 변경 감지에 의해 자동 update됨
    return new UserResponse(user);
  }
}