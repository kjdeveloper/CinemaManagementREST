package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHistoryTicketDto {

    private Long userId;
    private Boolean sendMail;
    private Boolean saveToFile;
}
