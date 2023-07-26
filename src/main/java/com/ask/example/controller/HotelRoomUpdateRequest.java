package com.ask.example.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class HotelRoomUpdateRequest {

    @NotNull(message = "roomType can't be null")
    private HotelRoomType roomType;

    @NotNull(message = "originalPrice can't be null")
    @Min(value = 0, message = "originalPrice must be larger than 0")
    private BigDecimal originalPrice;
}
