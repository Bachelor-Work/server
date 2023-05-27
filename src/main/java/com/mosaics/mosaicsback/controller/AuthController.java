package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.dto.user.AuthResponseDTO;
import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.dto.user.UserInfoDTO;
import com.mosaics.mosaicsback.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authService.loginUser(userDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        return authService.registerUser(userDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoDTO> getUserData(@NonNull Authentication authentication) {
        return new ResponseEntity<>(authService.chackUserInfo(authentication), HttpStatus.OK);
    }
}