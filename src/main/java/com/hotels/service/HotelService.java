package com.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotels.entity.HotelEntity;
import com.hotels.repository.HotelRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelEntity> getListHotel() {
        return hotelRepository.findAll();
    }
}