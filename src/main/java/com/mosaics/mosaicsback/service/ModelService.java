package com.mosaics.mosaicsback.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ModelService {

    void uploadModelInMuseum(MultipartFile multipartFile, Long museumId) throws IOException;

}
