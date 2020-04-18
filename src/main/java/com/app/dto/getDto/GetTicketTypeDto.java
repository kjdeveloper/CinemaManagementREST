package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTicketTypeDto {

    private Long id;
    private Long version;
    private String name;

    @Override
    public String toString() {
        return  " " + name + " ";
    }
}
