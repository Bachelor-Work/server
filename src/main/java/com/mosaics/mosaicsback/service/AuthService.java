package com.mosaics.mosaicsback.service;

import com.mosaics.mosaicsback.dto.user.AuthResponseDTO;
import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.dto.user.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthResponseDTO loginUser(UserDTO userDTO);

    ResponseEntity<String> registerUser(UserDTO userDTO);

    UserInfoDTO chackUserInfo(Authentication authentication);

}
