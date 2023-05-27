package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.config.JWTGenerator;
import com.mosaics.mosaicsback.dto.user.AuthResponseDTO;
import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.dto.user.UserInfoDTO;
import com.mosaics.mosaicsback.entity.user.Role;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.mapper.UserInfoMapper;
import com.mosaics.mosaicsback.mapper.UserMapper;
import com.mosaics.mosaicsback.repository.RoleRepository;
import com.mosaics.mosaicsback.repository.UserRepository;
import com.mosaics.mosaicsback.util.EmailSender;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(
                userDTO.getEmail(),
                userDTO.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(token1);
        User user = userRepository.findByEmail(userDTO.getEmail());
        List<Role> roles = user.getRoles().stream().toList();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token, user.getId(), user.getNickname(), user.getEmail(), roles), HttpStatus.OK);
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

            EmailSender.sendEmail(userDTO.getEmail());

        } else if (userRepository.findByEmail(userDTO.getEmail()).getEmail().equals(userDTO.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoDTO> getUserData(@NonNull Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        UserInfoDTO userDTO = UserInfoMapper.toDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}