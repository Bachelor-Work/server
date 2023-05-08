package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.entity.Model;
import com.mosaics.mosaicsback.entity.museum.Museum;

public class MuseumModelsMapper {
    public static MuseumModelsDTO toDto(Museum museum) {
        return MuseumModelsDTO.builder()
                .museumName(museum.getMuseumName())
                .museumDescription(museum.getMuseumDescription())
                .instagramURL(museum.getInstagramURL())
                .twitterURL(museum.getTwitterURL())
                .fileContent(museum.getMuseumImg().getFileContent())
                .modelName(museum.getModel().stream().map(Model::getFileName).toList())
                .modelDescription(museum.getModel().stream().map(Model::getDescription).toList())
                .modelContent(museum.getModel().stream().map(Model::getFileContent).toList())
                .build();
    }
}
