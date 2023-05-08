package com.mosaics.mosaicsback.service;

import com.mosaics.mosaicsback.dto.MuseumModelsDTO;

import java.util.Map;

public interface MuseumService {

    Map<String, Object> getBackAllMuseums(int pageNum);
    MuseumModelsDTO getBackMuseumById(Long museumId);

    Map<String, Object> getBackMuseumsByName(int pageNum, String name);

}
