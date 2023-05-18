package com.mosaics.mosaicsback.controller;

import com.mosaics.mosaicsback.entity.museum.ManagerRequest;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.repository.ManagerRequestRepository;
import com.mosaics.mosaicsback.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final ManagerRequestRepository repository;

    @PostMapping("/request")
    public ResponseEntity<Void> requestManagerRole(@NonNull Authentication authentication,
                                                   @NonNull @RequestParam("description") String description) {

        User user = userService.findByEmail(authentication.getName());
        if (repository.findByUserId(user.getId()).isEmpty())
            repository.save(ManagerRequest.builder()
                    .description(description)
                    .status(false)
                    .user(user)
                    .build());
        else
            return ResponseEntity.badRequest().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }


}
