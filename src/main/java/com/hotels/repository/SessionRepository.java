package com.hotels.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotels.entity.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
    Optional<SessionEntity> findByAccessTokenAndExpiredAtGreaterThan(String token, LocalDate currentDate);
}
