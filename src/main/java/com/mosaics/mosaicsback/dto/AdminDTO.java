package com.mosaics.mosaicsback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminDTO {

    private Long user_id;
    private String userEmail;
    private String userDesc;
    private Boolean status;

}
