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
    private byte @NonNull [] fileContent;
    private String dominantColor;

    private List<String> modelName;
    private List<String> modelDescription;
    private List<byte []> modelContent;

}
