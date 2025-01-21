package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.model.LoginRequest;
import com.example.Project_BE.Project_BE.securityNew.JwtTokenUtil;
import com.example.Project_BE.Project_BE.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")  // Endpoint untuk otentikasi
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;  // Untuk mengelola otentikasi

    @Autowired
    private JwtTokenUtil jwtTokenUtil;  // Untuk menghasilkan dan memvalidasi JWT

    @Autowired
    private AuthService authService;  // Service untuk otentikasi

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Mengautentikasi pengguna dan mendapatkan token
            Map<String, Object> response = authService.authenticate(loginRequest);
            return ResponseEntity.ok(response);  // Mengembalikan response dengan status OK
        } catch (BadCredentialsException e) {
            // Menangani kesalahan otentikasi (email atau password salah)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email atau password salah."));
        } catch (Exception e) {
            logger.error("Terjadi kesalahan saat login: ", e);  // Logging kesalahan
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Terjadi kesalahan di server."));  // Mengembalikan kesalahan server
        }
    }

}
