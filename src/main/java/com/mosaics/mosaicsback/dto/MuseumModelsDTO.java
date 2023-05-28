package com.mosaics.mosaicsback.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuseumModelsDTO {

    @NonNull
    private String museumName;

    @NonNull
    private String museumDescription;

    private String instagramURL;
    private String twitterURL;
    private byte @NonNull [] imgContent;
    private String imgType;

    private List<ModelDTO> modelDTO;

}
