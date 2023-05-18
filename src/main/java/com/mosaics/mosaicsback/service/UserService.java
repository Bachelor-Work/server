package com.mosaics.mosaicsback.service;

import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO register(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    User findByEmail(String email);

    UserDTO login(UserDTO userDTO);
}