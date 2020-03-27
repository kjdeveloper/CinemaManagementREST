package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTicketDto {

    private Long id;
    private GetUserDto user;
    private Long version;
    private GetFilmShowDto filmShow;

}
