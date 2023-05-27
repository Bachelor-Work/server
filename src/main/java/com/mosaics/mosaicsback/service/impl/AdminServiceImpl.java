package com.mosaics.mosaicsback.service.impl;

import com.mosaics.mosaicsback.dto.AdminDTO;
import com.mosaics.mosaicsback.entity.museum.ManagerRequest;
import com.mosaics.mosaicsback.entity.user.User;
import com.mosaics.mosaicsback.repository.ManagerRequestRepository;
import com.mosaics.mosaicsback.repository.RoleRepository;
import com.mosaics.mosaicsback.repository.UserRepository;
import com.mosaics.mosaicsback.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ManagerRequestRepository managerRequestRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<AdminDTO> getAllManagerRequest() {
        List<ManagerRequest> managerRequests = managerRequestRepository.findAll();

        return managerRequests.stream()
                .map(managerRequest -> AdminDTO.builder()
                        .user_id(managerRequest.getUser().getId())
                        .userEmail(managerRequest.getUser().getEmail())
                        .userDesc(managerRequest.getDescription())
                        .status(managerRequest.getStatus())
                        .build())
                .toList();
    }

    @Override
    public void allowManagerRole(Long userId) {
        Optional<ManagerRequest> managerRequest = managerRequestRepository.findByUserId(userId);
        if (managerRequest.isPresent()) {
            managerRequestRepository.delete(managerRequestRepository.findByUserId(userId).orElseThrow());
            managerRequest.get().setStatus(true);
            managerRequestRepository.save(managerRequest.get());
        }

        User user = userRepository.findById(userId).orElseThrow();
        user.addRole(roleRepository.findByName("MANAGER"));
        userRepository.save(user);
    }
}
