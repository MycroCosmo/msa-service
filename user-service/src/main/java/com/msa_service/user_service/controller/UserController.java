package com.msa_service.user_service.controller;

import com.msa_service.user_service.dto.UserRequestDto;
import com.msa_service.user_service.dto.UserResponseDto;
import com.msa_service.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
    return ResponseEntity.ok(userService.createUser(requestDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }
}
