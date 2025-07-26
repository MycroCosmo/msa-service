package com.msa_service.user_service.service;

import com.msa_service.user_service.domain.User;
import com.msa_service.user_service.dto.request.LoginRequest;
import com.msa_service.user_service.dto.response.LoginResponse;
import com.msa_service.user_service.exception.CustomException;
import com.msa_service.user_service.exception.ErrorCode;
import com.msa_service.user_service.repository.UserRepository;
import com.msa_service.user_service.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
    }

    String accessToken = jwtTokenProvider.generateAccessToken(user);
    String refreshToken = jwtTokenProvider.generateRefreshToken(user);

    return new LoginResponse(accessToken, refreshToken);
  }
}

