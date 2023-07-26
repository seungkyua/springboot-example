package com.ask.example.controller;

import com.ask.example.domain.HotelRoomNumber;
import com.ask.example.utils.IdGenerator;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController
public class HotelRoomController {

    private static final String HEADER_CREATED_AT = "X-CREATED-AT";
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public HotelRoomResponse getHotelRoomByPeriod(
            ClientInfo clientInfo,
            @PathVariable(value = "hotelId") Long hotelId,
            @PathVariable String roomNumber,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate
            ) {

        System.out.println(clientInfo);

        Long hotelRoomId = IdGenerator.create();
        BigDecimal originalPrice = new BigDecimal("130.00");

        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber, HotelRoomType.DOUBLE, originalPrice);
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate))
            fromDate.datesUntil(toDate.plusDays(1)).forEach(response::reservedAt);

        return response;
    }

//    // HotelRoomNumber binding example
//    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
//    public HotelRoomResponse getHotelRoomByPeriod(
//            @PathVariable Long hotelId,
//            @PathVariable HotelRoomNumber roomNumber,
//            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
//            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate
//            ) {
//
//        Long hotelRoomId = IdGenerator.create();
//        BigDecimal originalPrice = new BigDecimal("130.00");
//
//        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber.toString(), HotelRoomType.DOUBLE, originalPrice);
//        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate))
//            fromDate.datesUntil(toDate.plusDays(1)).forEach(date -> response.reservedAt(date));
//
//        return response;
//    }
}
