package com.mosaics.mosaicsback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuseumDTO {

    private String museumName;
    private String museumDescription;
    private String instagramURL;
    private String twitterURL;
    private byte @NonNull [] fileContent;
    private String dominantColor;
    private String museumType;

}
