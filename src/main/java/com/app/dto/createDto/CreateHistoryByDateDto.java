package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHistoryByDateDto {

    private Long userId;
    private LocalDate from;
    private LocalDate to;
    private Boolean sendMail;
    private Boolean getFile;

}
