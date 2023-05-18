package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.service.MuseumService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/museums")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@AllArgsConstructor
public class MuseumController {
    MuseumService museumService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> returnAllMuseums(@RequestParam(defaultValue = "0", value = "pageNum") int pageNum,
                                                                @RequestParam(value = "sortByName", required = false) String name,
                                                                @RequestParam(value = "sortByCategory", required = false) String category) {
        Map<String, Object> response = (name == null)
                ? museumService.getBackAllMuseums(pageNum) : museumService.getBackMuseumsByName(pageNum, name, category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{museumId}")
    public ResponseEntity<MuseumModelsDTO> returnMuseumById(@PathVariable Long museumId) {
        MuseumModelsDTO museum = museumService.getBackMuseumById(museumId);

        return new ResponseEntity<>(museum, HttpStatus.OK);
    }

    @PostMapping("/create-museum")
    public ResponseEntity<Void> uploadMuseumImg(@NonNull @RequestParam("avatar") MultipartFile multipartFile,
                                                @NonNull @RequestParam("museumInfo") String museumInfo,
                                                @NonNull Authentication authentication) throws IOException {

        boolean created = museumService.createMuseum(multipartFile, museumInfo, authentication);
        if (!created)
            return ResponseEntity.badRequest().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }
}