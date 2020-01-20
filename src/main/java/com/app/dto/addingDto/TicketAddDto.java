package com.app.dto.addingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketAddDto {

    private Integer orderedQuantity;
    private Boolean reservation;
    private Set<UserAddDto> users;
    private Set<FilmShowAddDto> filmShows;
}
