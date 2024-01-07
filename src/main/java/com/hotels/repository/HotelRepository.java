package com.hotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotels.entity.HotelEntity;

public interface HotelRepository extends JpaRepository<HotelEntity, String> {

}
