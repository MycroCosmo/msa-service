package com.msa_service.user_service.repository;

import com.msa_service.user_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}