package com.ask.example.controller;

import com.ask.example.controller.validator.HotelRoomReserveValidator;
import com.ask.example.domain.reservation.ReserveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelRoomReserveController {

    private final ReserveService reserveService;

    public HotelRoomReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new HotelRoomReserveValidator());
    }

    @PostMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}/reserve")
    public ResponseEntity<ErrorResponse> reserveHotelRoomByRoomNumber(
//    public ResponseEntity<HotelRoomIdResponse> reserveHotelRoomByRoomNumber(
            @PathVariable Long hotelId,
            @PathVariable String roomNumber,
            @Valid @RequestBody HotelRoomReserveRequest reserveRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuilder("validation error.")
                    .append(" field: ").append(fieldError.getField())
                    .append(", code: ").append(fieldError.getCode())
                    .append(", message: ").append(fieldError.getDefaultMessage())
                    .toString();

            System.out.println(errorMessage);
            return new ResponseEntity<>(
                    new ErrorResponse(errorMessage),
                    HttpStatus.BAD_REQUEST
            );
//            return ResponseEntity.badRequest().build();
        }

        Long reservationId = reserveService.reserveHotelRoom(
                hotelId, roomNumber, reserveRequest.getCheckInDate(), reserveRequest.getCheckOutDate());

        HotelRoomIdResponse body = HotelRoomIdResponse.from(reservationId);
        return new ResponseEntity<>(
                new ErrorResponse(reservationId.toString()),
                HttpStatus.OK
        );
//        return ResponseEntity.ok(body);
    }
}
