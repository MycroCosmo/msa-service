package com.msa_service.user_service.controller;

import com.msa_service.user_service.dto.request.UserRequest;
import com.msa_service.user_service.dto.request.UserUpdateDto;
import com.msa_service.user_service.dto.response.UserResponse;
import com.msa_service.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest requestDto) {
    return ResponseEntity.ok(userService.createUser(requestDto));
  }

  /**
   * 사용자 정보 조회
   * @param id 유저 id
   * @return 해당 유저
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUser(id));
  }

  /**
   * 전체 유저 조회
   * @return 전체 유저
   */
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * 유저 정보 업데이트
   * @param id
   * @param request
   * @return
   */
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDto request) {
    return ResponseEntity.ok(userService.updateUser(id,request));
  }


}
