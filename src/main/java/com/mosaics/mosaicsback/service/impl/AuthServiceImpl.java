package com.mosaics.mosaicsback.service.impl;

import com.mosaics.mosaicsback.config.JWTGenerator;
import com.mosaics.mosaicsback.dto.user.AuthResponseDTO;
import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.dto.user.UserInfoDTO;
import com.mosaics.mosaicsback.entity.user.Role;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.mapper.UserInfoMapper;
import com.mosaics.mosaicsback.repository.RoleRepository;
import com.mosaics.mosaicsback.repository.UserRepository;
import com.mosaics.mosaicsback.service.AuthService;
import com.mosaics.mosaicsback.util.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO loginUser(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(userDTO.getEmail());
        List<Role> roles = user.getRoles().stream().toList();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new AuthResponseDTO(token, user.getId(), user.getNickname(), user.getEmail(), roles);
    }

    @Override
    public ResponseEntity<String> registerUser(UserDTO userDTO) {
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
            return new ResponseEntity<>("User registered success!", HttpStatus.OK);

        } else if (userRepository.findByEmail(userDTO.getEmail()).getEmail().equals(userDTO.getEmail()))
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);

        return null;
    }

    @Override
    public UserInfoDTO chackUserInfo(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        return UserInfoMapper.toDto(user);
    }


}
