package com.app.dto.downloadingDto;

import com.app.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaDownloadDto {

    private Long id;
    private String name;
    private City city;
}
