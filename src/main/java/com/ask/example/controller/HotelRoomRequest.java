package com.ask.example.controller;

import com.ask.example.domain.HotelRoomType;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class HotelRoomRequest {

    private String roomNumber;

    private HotelRoomType roomType;

    private BigDecimal originalPrice;
}
