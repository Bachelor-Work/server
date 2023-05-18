package com.mosaics.mosaicsback.dto.user;

import com.mosaics.mosaicsback.entity.museum.Museum;
import com.mosaics.mosaicsback.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nickname;
    private String password;
    private String email;
    private Collection<Role> roles;
    private Museum museum;
}