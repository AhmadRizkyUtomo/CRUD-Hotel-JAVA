package com.hotels.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hotels.entity.HotelEntity;
import com.hotels.entity.SessionEntity;
import com.hotels.model.HotelRequest;
import com.hotels.model.Response;
import com.hotels.repository.SessionRepository;
import com.hotels.service.HotelService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private SessionRepository sessionRepository;

    private static final String RoleAdmin = "ADMIN";

    private SessionEntity checkUserSession(HttpServletRequest request) {
        String accessToken = request.getHeader("X-Authorization-Token");
        if (accessToken == null || accessToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "missing access token");
        }

        SessionEntity session = sessionRepository.findByAccessTokenAndExpiredAtGreaterThan(accessToken, LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "invalid access token"));

        return session;
    }

    @GetMapping(path = "/api/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<HotelEntity>> getListHotel(HttpServletRequest header) {
        checkUserSession(header);

        List<HotelEntity> data = hotelService.getListHotel();
        Response<List<HotelEntity>> response = Response.<List<HotelEntity>>builder().data(data).build();
        response.setStatus("success");
        response.setMessage("success get list hotel");

        return response;
    }

    @PostMapping(path = "/api/hotels", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> insertHotel(@RequestBody HotelRequest request, HttpServletRequest header) {
        SessionEntity entity = checkUserSession(header);
        if (!entity.getRole().equals(RoleAdmin)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "only admin can access this endpoint");
        }

        hotelService.insertHotel(request);
        Response<String> resp = Response.<String>builder().data("ok").build();
        resp.setStatus("Success");
        resp.setMessage("Success insert new date");

        return resp;
    }
}
