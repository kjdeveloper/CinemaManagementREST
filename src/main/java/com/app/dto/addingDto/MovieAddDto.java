package com.app.dto.addingDto;

import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAddDto {

    private String title;
    private String description;
    private String director;
    private int duration;
    private Genre genre;

}
