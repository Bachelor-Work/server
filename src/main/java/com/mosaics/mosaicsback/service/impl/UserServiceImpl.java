package com.mosaics.mosaicsback.service.impl;

import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.mapper.UserMapper;
import com.mosaics.mosaicsback.repository.UserRepository;
import com.mosaics.mosaicsback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserDTO userDTO) {
        String encodedPass = passwordEncoder.encode(userDTO.getPassword());
        User user = userRepository.save(User.builder()
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .password(encodedPass)
                .build());

        return UserMapper.toDto(user);
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return UserMapper.toDto(user);
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}