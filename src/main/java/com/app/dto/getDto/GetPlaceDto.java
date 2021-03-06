package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPlaceDto {

    private Long id;
    private Long version;
    private boolean available;
    private Integer row_num;
    private Integer number;


}
