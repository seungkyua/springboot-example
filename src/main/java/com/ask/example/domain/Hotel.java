package com.ask.example.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Hotel {
    private final Long hotelId;
    private final String name;
    private final String address;
    private final Integer roomCount;

    public Hotel(Long hotelId, String name, String address, Integer roomCount) {
        if (Objects.isNull(hotelId) || Objects.isNull(name) || Objects.isNull(address))
            throw new IllegalArgumentException("Invalid hotel constraint");

        if (Objects.isNull(roomCount) || roomCount <= 0)
            throw new IllegalArgumentException("Invalid room count");

        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.roomCount = roomCount;
    }
}
