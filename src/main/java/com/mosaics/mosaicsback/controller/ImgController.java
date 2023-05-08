package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.entity.museum.MuseumImg;
import com.mosaics.mosaicsback.repository.MuseumImgRepository;
import com.mosaics.mosaicsback.repository.MuseumRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/museums/{museumId}")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@AllArgsConstructor
public class ImgController {

    MuseumRepository museumRepository;
    MuseumImgRepository museumImgRepository;

    @PostMapping("/uploadimg")
    public ResponseEntity<Void> uploadMusemImg(@NonNull @RequestParam("avatar") MultipartFile multipartFile, @PathVariable Long museumId) throws IOException {
        MuseumImg img = new MuseumImg(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                museumRepository.getMuseumById(museumId)
        );

        Optional<MuseumImg> museumImg = Optional.ofNullable(museumRepository.getMuseumById(museumId).getMuseumImg());
        museumImg.ifPresent(value -> museumImgRepository.deleteById(value.getId()));

        museumImgRepository.save(img);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }
}
