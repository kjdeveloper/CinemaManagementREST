package com.app.model.enums;

import lombok.Getter;

import java.math.BigDecimal;

//jako entity moge zrobic
@Getter
public enum TicketType {

    NORMAL(new BigDecimal(25)), REDUCED(new BigDecimal(15)), FAMILY(new BigDecimal(20));

    private BigDecimal price;

    TicketType(BigDecimal price) {
        this.price = price;
    }
}
