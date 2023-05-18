package com.mosaics.mosaicsback.service;

import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface MuseumService {

    Map<String, Object> getBackAllMuseums(int pageNum);

    MuseumModelsDTO getBackMuseumById(Long museumId);

    Map<String, Object> getBackMuseumsByName(int pageNum, String name, String category);

    boolean createMuseum(MultipartFile multipartFile, String museumInfo, Authentication authentication) throws IOException;

}
