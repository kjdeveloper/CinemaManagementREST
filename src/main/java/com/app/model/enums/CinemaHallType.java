package com.app.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CinemaHallType {

    VIP(5, 10), SMALL(3, 10), MEDIUM(10, 10), BIG(20, 15);

    private final int rowNum;
    private final int number;

}
