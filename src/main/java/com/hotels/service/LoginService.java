package com.hotels.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotels.entity.LoginEntity;
import com.hotels.entity.SessionEntity;
import com.hotels.model.LoginRequest;
import com.hotels.model.LoginResponse;
import com.hotels.repository.LoginRepository;
import com.hotels.repository.SessionRepository;

@Service
public class LoginService {
        @Autowired
        private LoginRepository loginRepository;
        @Autowired
        private SessionRepository sessionRepository;

        public LoginResponse login(LoginRequest request) {
                LoginEntity auth = loginRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                                "wrong username or password"));

                // generate token
                String accessToken = generateRandomString(20);

                // save db
                SessionEntity newSession = new SessionEntity();
                newSession.setRole(auth.getRole());
                newSession.setExpiredAt(LocalDate.now().plusDays(3));
                newSession.setCreatedAt(LocalDate.now());
                newSession.setAccessToken(accessToken);

                sessionRepository.save(newSession);

                // return token
                LoginResponse resp = new LoginResponse();
                resp.setAccess_token(accessToken);
                resp.setRole(auth.getRole());

                return resp;
        }

        private static String generateRandomString(int length) {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                StringBuilder randomString = new StringBuilder();

                Random random = new Random();
                for (int i = 0; i < length; i++) {
                        int index = random.nextInt(characters.length());
                        randomString.append(characters.charAt(index));
                }

                return randomString.toString();
        }
}
