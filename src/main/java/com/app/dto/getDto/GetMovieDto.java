package com.app.dto.getDto;

import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMovieDto {

    private Long id;
    private String title;
    private String description;
    private String director;
    private int duration;
    private Genre genre;
    private Long version;
    private Set<GetUserDto> usersAddedThisMovieToFavourites;
    private Set<GetFilmShowDto> filmShows;
}
