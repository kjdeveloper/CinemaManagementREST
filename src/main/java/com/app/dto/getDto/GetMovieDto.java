package com.app.dto.getDto;

import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMovieDto {

    private Long id;
    private Long version;
    private String title;
    private String description;
    private String director;
    private int duration;
    private Genre genre;

}
