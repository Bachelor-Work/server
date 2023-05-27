package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.user.UserInfoDTO;
import com.mosaics.mosaicsback.entity.user.Role;
import com.mosaics.mosaicsback.entity.user.User;

public class UserInfoMapper {

    public static UserInfoDTO toDto(User user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRoles().stream().map(Role::getName).toList())
                .build();
    }
}
