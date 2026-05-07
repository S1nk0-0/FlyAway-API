package com.flyaway.controller;

import com.flyaway.repository.BookingRepository;
import com.flyaway.repository.FlightRepository;
import com.flyaway.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CleanupController {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public CleanupController(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }

    @DeleteMapping("/cleanup")
    @Transactional
    public ResponseEntity<Void> cleanup() {
        bookingRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        flightRepository.deleteAllInBatch();
        return ResponseEntity.ok().build();
    }
}
