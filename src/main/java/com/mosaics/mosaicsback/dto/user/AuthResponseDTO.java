package com.mosaics.mosaicsback.dto.user;

import com.mosaics.mosaicsback.entity.user.Role;
import lombok.Data;

import java.util.List;

@Data
public class AuthResponseDTO {

    private Long id;
    private String email;
    private List<String> role;
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken, Long id, String email, List<Role> role) {
        this.accessToken = accessToken;
        this.id = id;
        this.role = role.stream().map(Role::getName).toList();
        this.email = email;
    }
}
