package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.museum.MuseumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MuseumTypeRepository extends JpaRepository<MuseumType, Long> {

    @Query("select t.type from MuseumType t")
    List<String> getListOfTypes();

    @Transactional
    @Query("select t from MuseumType t where t.type = :type")
    MuseumType getByType(String type);

}
