package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.config.JWTGenerator;
import com.mosaics.mosaicsback.dto.user.AuthResponseDTO;
import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.entity.user.Role;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.repository.RoleRepository;
import com.mosaics.mosaicsback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) == null) {
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode((userDTO.getPassword())))
                    .nickname(userDTO.getNickname())
                    .build();

            Role roles = roleRepository.findByName("USER");
            user.setRoles(Collections.singletonList(roles));

            userRepository.save(user);
        } else if (userRepository.findByEmail(userDTO.getEmail()).getEmail().equals(userDTO.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}