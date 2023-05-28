package com.mosaics.mosaicsback.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ModelDTO {

    private String modelName;
    private String modelDescription;
    private byte [] modelContent;
    private String modelType;

}
