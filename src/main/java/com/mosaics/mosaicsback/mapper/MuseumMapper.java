package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.MuseumDTO;
import com.mosaics.mosaicsback.entity.museum.Museum;

public class MuseumMapper {

    public static MuseumDTO toDto(Museum museum) {

        return MuseumDTO.builder()
                .museumName(museum.getMuseumName())
                .museumDescription(museum.getMuseumDescription())
                .instagramURL(museum.getInstagramURL())
                .twitterURL(museum.getTwitterURL())
                .fileContent(museum.getMuseumImg().getFileContent())
                .museumType(museum.getMuseumType().getType())
                .build();

    }

}
