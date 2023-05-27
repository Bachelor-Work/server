package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.service.ModelService;
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

    private final ModelService modelService;

    @PostMapping("/upload-model")
    public ResponseEntity<Void> uploadMuseumModel(@NonNull @RequestParam("model") MultipartFile multipartFile, @PathVariable Long museumId) throws IOException {
        modelService.uploadModelInMuseum(multipartFile, museumId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

}
