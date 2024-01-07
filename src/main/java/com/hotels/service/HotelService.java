package com.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotels.entity.HotelEntity;
import com.hotels.model.HotelRequest;
import com.hotels.repository.HotelRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelEntity> getListHotel() {
        return hotelRepository.findAll();
    }

    public void insertHotel(HotelRequest request) {
        HotelEntity hotel = new HotelEntity();
        hotel.setFacilities(request.getFacilities());
        hotel.setLocation(request.getLocation());
        hotel.setReview(request.getReview());
        hotel.setPrice(request.getPrice());
        hotel.setName(request.getName());

        hotelRepository.save(hotel);
    }

    public void updateHotel(HotelRequest request) {
        HotelEntity hotel = new HotelEntity();
        hotel.setFacilities(request.getFacilities());
        hotel.setLocation(request.getLocation());
        hotel.setReview(request.getReview());
        hotel.setPrice(request.getPrice());
        hotel.setName(request.getName());
        hotel.setId(request.getId());

        hotelRepository.save(hotel);
    }

    public void deleteHotel(String id) {
        hotelRepository.deleteById(id);
    }
}