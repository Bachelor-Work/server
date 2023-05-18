package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.dto.AdminDTO;
import com.mosaics.mosaicsback.entity.museum.ManagerRequest;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.repository.ManagerRequestRepository;
import com.mosaics.mosaicsback.repository.RoleRepository;
import com.mosaics.mosaicsback.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final ManagerRequestRepository managerRequestRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping()
    public ResponseEntity<List<AdminDTO>> returnAllManagerRequest() {

        List<ManagerRequest> managerRequests = managerRequestRepository.findAll();

        List<AdminDTO> response = managerRequests.stream()
                .map(managerRequest -> AdminDTO.builder()
                        .user_id(managerRequest.getUser().getId())
                        .userEmail(managerRequest.getUser().getEmail())
                        .userDesc(managerRequest.getDescription())
                        .status(managerRequest.getStatus())
                        .build())
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/allow/{userId}")
    public ResponseEntity<Void> allowManager(@NonNull @PathVariable Long userId) {

        Optional<ManagerRequest> managerRequest = managerRequestRepository.findByUserId(userId);
        if (managerRequest.isPresent()) {
            managerRequestRepository.delete(managerRequestRepository.findByUserId(userId).orElseThrow());
            managerRequest.get().setStatus(true);
            managerRequestRepository.save(managerRequest.get());
        }

        User user = userRepository.findById(userId).orElseThrow();
        user.addRole(roleRepository.findByName("MANAGER"));
        userRepository.save(user);

        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/admin");
        return new ResponseEntity<>(header, HttpStatus.FOUND);
    }

}
