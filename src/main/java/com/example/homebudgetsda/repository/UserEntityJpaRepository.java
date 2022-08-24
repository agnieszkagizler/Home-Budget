package com.example.homebudgetsda.repository;

import com.example.homebudgetsda.dto.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUsername(String username);
}
