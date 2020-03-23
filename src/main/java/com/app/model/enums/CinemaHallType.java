package com.app.model.enums;

public enum CinemaHallType {

    VIP(5, 10), SMALL(3, 10), MEDIUM(10, 10), BIG(20, 15);

    private int row;
    private int number;

    CinemaHallType(int row, int number) {
        this.row = row;
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }
}
