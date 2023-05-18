package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.museum.ManagerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRequestRepository extends JpaRepository<ManagerRequest, Long> {

    Optional<ManagerRequest> findByUserId(Long id);

}
