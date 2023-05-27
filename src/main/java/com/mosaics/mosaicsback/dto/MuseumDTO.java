package com.mosaics.mosaicsback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MuseumDTO {

    private Long id;
    private String museumName;
    private String museumDescription;
    private String instagramURL;
    private String twitterURL;
    private byte @NonNull [] imgContent;
    private String imgType;
    private String dominantColor;
    private String museumType;

}
