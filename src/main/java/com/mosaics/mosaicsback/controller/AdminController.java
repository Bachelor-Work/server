package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.dto.AdminDTO;
import com.mosaics.mosaicsback.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping()
    public ResponseEntity<List<AdminDTO>> returnAllManagerRequest() {

        return new ResponseEntity<>(adminService.getAllManagerRequest(), HttpStatus.OK);
    }

    @PostMapping("/allow/{userId}")
    public ResponseEntity<Void> allowManager(@NonNull @PathVariable Long userId) {
        adminService.allowManagerRole(userId);

        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/admin");
        return new ResponseEntity<>(header, HttpStatus.FOUND);
    }

}
