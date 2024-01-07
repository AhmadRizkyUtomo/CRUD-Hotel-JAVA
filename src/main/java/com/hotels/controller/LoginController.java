package com.hotels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.hotels.model.LoginRequest;
import com.hotels.model.LoginResponse;
import com.hotels.model.Response;
import com.hotels.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<LoginResponse> auth(@RequestBody LoginRequest request) {
        LoginResponse res = loginService.login(request);

        Response<LoginResponse> response = Response.<LoginResponse>builder().data(res).build();
        response.setStatus("success");
        response.setMessage("Login successful");

        return response;
    }

}
