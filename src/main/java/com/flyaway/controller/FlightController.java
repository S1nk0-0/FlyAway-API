package com.flyaway.controller;

import com.flyaway.dto.BookingRequest;
import com.flyaway.dto.BookingResponse;
import com.flyaway.dto.CreateManyRequest;
import com.flyaway.dto.FlightCreateRequest;
import com.flyaway.service.BookingService;
import com.flyaway.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FlightController {
    private final FlightService flightService;
    private final BookingService bookingService;

    public FlightController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @PostMapping("/flights/create")
    public ResponseEntity<Map<String, Object>> createFlight(@Valid @RequestBody FlightCreateRequest req) {
        Long id = flightService.createFlight(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @PostMapping("/flights/create-many")
    public ResponseEntity<Map<String, Object>> createManyFlights(@Valid @RequestBody CreateManyRequest req) {
        List<Long> ids = new ArrayList<>();
        for (var input : req.getInputs()) {
            ids.add(flightService.createFlight(input));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("ids", ids));
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<?> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/flights/search")
    public ResponseEntity<Map<String, Object>> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) String estDepartureTimeFrom,
            @RequestParam(required = false) String estDepartureTimeTo) {
        var items = flightService.searchFlights(flightNumber, airlineName,
                estDepartureTimeFrom, estDepartureTimeTo);
        return ResponseEntity.ok(Map.of("items", items));
    }

    @PostMapping("/flights/book")
    public ResponseEntity<Map<String, Object>> bookFlight(@Valid @RequestBody BookingRequest req,
                                                           Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        Long bookingId = bookingService.book(userId, req);
        return ResponseEntity.ok(Map.of("id", bookingId));
    }

    @GetMapping("/flights/book/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
}
