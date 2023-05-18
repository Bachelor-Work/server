package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.user.UserDTO;
import com.mosaics.mosaicsback.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles()) // .stream().map(Role::getName).toList()
                .museum(user.getMuseum())
                .build();
    }

}