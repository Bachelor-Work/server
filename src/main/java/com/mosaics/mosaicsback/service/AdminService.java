package com.mosaics.mosaicsback.service;

import com.mosaics.mosaicsback.dto.AdminDTO;

import java.util.List;

public interface AdminService {

    List<AdminDTO> getAllManagerRequest();

    void allowManagerRole(Long userId);

}
