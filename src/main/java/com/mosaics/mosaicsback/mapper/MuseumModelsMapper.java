package com.mosaics.mosaicsback.mapper;

import com.mosaics.mosaicsback.dto.ModelDTO;
import com.mosaics.mosaicsback.dto.MuseumDTO;
import com.mosaics.mosaicsback.dto.MuseumModelsDTO;
import com.mosaics.mosaicsback.entity.Model;
import com.mosaics.mosaicsback.entity.museum.Museum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MuseumModelsMapper {

    private MuseumModelsMapper() {throw new IllegalStateException("Mapper utility class");}

    public static MuseumModelsDTO toDto(Museum museum) {
        List<ModelDTO> list = museum.getModel().stream().map(model -> ModelDTO.builder()
                        .modelName(model.getFileName())
                        .modelDescription(model.getDescription())
                        .modelType(model.getTypeOfFile())
                        .modelContent(model.getFileContent())
                        .build())
                .toList();


        return MuseumModelsDTO.builder()
                .museumName(museum.getMuseumName())
                .museumDescription(museum.getMuseumDescription())
                .instagramURL(museum.getInstagramURL())
                .twitterURL(museum.getTwitterURL())
                .imgContent(museum.getMuseumImg().getFileContent())
                .imgType(museum.getMuseumImg().getTypeOfFile())
                .modelDTO(list)
                .build();
    }
}
