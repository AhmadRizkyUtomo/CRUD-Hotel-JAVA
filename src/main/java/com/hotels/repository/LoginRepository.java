package com.hotels.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotels.entity.LoginEntity;

public interface LoginRepository extends JpaRepository<LoginEntity, String> {
    Optional<LoginEntity> findByEmailAndPassword(String email, String password);
}
