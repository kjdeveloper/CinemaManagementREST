package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHistoryByPriceDto {

    private Long userId;
    private BigDecimal from;
    private BigDecimal to;
    private Boolean sendMail;
    private Boolean getFile;

}
