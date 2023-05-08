package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.service.MuseumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/museums")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@AllArgsConstructor
public class MuseumController {
    MuseumService museumService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> returnAllMuseums(@RequestParam(defaultValue = "0", value = "pageNum") int pageNum,
                                                                   @RequestParam(value = "sort", required = false) String name) {
        Map<String, Object> response = (name == null)
                ? museumService.getBackAllMuseums(pageNum) : museumService.getBackMuseumsByName(pageNum, name);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{museumId}")
    public ResponseEntity<MuseumModelsDTO> returnMuseumById(@PathVariable Long museumId) {
        MuseumModelsDTO museum = museumService.getBackMuseumById(museumId);

        return new ResponseEntity<>(museum, HttpStatus.OK);
    }
}