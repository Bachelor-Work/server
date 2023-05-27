package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.MuseumDTO;
import com.mosaics.mosaicsback.entity.museum.Museum;

public class MuseumMapper {

    private MuseumMapper() {throw new IllegalStateException("Mapper utility class");}

    public static MuseumDTO toDto(Museum museum) {
        return MuseumDTO.builder()
                .id(museum.getId())
                .museumName(museum.getMuseumName())
                .museumDescription(museum.getMuseumDescription())
                .instagramURL(museum.getInstagramURL())
                .twitterURL(museum.getTwitterURL())
                .imgContent(museum.getMuseumImg().getFileContent())
                .imgType(museum.getMuseumImg().getTypeOfFile())
                .dominantColor(museum.getMuseumImg().getDominantColor())
                .museumType(museum.getMuseumType().getType())
                .build();

    }

}
