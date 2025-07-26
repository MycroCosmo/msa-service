package com.msa_service.user_service.controller;

import com.msa_service.user_service.dto.UserRequestDto;
import com.msa_service.user_service.dto.UserResponseDto;
import com.msa_service.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 회원가입
   * @param requestDto 회원가입 정보
   * @return 회원가입한 유저 정보
   */
  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
    return ResponseEntity.ok(userService.createUser(requestDto));
  }

  /**
   * 사용자 정보 조회
   * @param id 유저 id
   * @return 해당 유저
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  /**
   * 전체 유저 조회
   * @return 전체 유저
   */
  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
