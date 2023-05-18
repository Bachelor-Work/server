package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.museum.Museum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MuseumRepository extends JpaRepository<Museum, Long> {

    @Transactional
    @Query("select m from Museum m where m.id = :id")
    Museum getMuseumById(Long id);

    @Transactional
    @Query("select m.museumName from Museum m")
    List<String> getAllMuseumsName();

    @Transactional
    @Query("select m from Museum m inner join MuseumType mt on mt.id = m.museumType.id where m.museumName like %:name% and mt.type like %:category%")
    Page<Museum> getAllMuseumsByName(String name, Pageable pageable, String category);

}