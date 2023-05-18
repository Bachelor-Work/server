package com.mosaics.mosaicsback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateMuseumDTO {

    private String museumName;
    private String museumDescription;
    private String instagramURL;
    private String twitterURL;
    private String museumType;
}
