package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.entity.Model;
import com.mosaics.mosaicsback.repository.ModelRepository;
import com.mosaics.mosaicsback.repository.MuseumRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/museums/{museumId}")
@AllArgsConstructor
public class ModelController {

    MuseumRepository museumRepository;
    ModelRepository modelRepository;

    @PostMapping("/upload-model")
    public ResponseEntity<Void> uploadMuseumModel(@NonNull @RequestParam("model") MultipartFile multipartFile, @PathVariable Long museumId) throws IOException {
        Model model = new Model(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                museumRepository.getMuseumById(museumId),
                multipartFile.getResource().getDescription()
        );

        modelRepository.save(model);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

}
