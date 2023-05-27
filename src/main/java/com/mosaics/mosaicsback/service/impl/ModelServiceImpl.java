package com.mosaics.mosaicsback.service.impl;

import com.mosaics.mosaicsback.entity.Model;
import com.mosaics.mosaicsback.repository.ModelRepository;
import com.mosaics.mosaicsback.repository.MuseumRepository;
import com.mosaics.mosaicsback.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final MuseumRepository museumRepository;
    private final ModelRepository modelRepository;

    @Override
    public void uploadModelInMuseum(MultipartFile multipartFile, Long museumId) throws IOException {
        Model model = new Model(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                museumRepository.getMuseumById(museumId),
                multipartFile.getResource().getDescription()
        );

        modelRepository.save(model);
    }
}
