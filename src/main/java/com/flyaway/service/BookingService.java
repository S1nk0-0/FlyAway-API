package com.flyaway.service;

import com.flyaway.dto.BookingRequest;
import com.flyaway.dto.BookingResponse;
import com.flyaway.entity.Booking;
import com.flyaway.entity.Flight;
import com.flyaway.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository,
                          FlightService flightService,
                          UserService userService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userService = userService;
    }

    @Transactional
    public Long book(Long userId, BookingRequest req) {
        var user = userService.getUserById(userId);
        var flight = flightService.getFlightEntityById(req.getFlightId());

        // Check seats
        if (flight.getAvailableSeats() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No seats available");
        }

        // Nice-to-have: check past flight
        LocalDateTime now = LocalDateTime.now();
        if (flight.getEstDepartureTime().isBefore(now) || flight.getEstArrivalTime().isBefore(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight has already departed or is in transit");
        }

        // Nice-to-have: check scheduling conflict
        List<Booking> otherBookings = bookingRepository.findByCustomerIdExcludingFlight(userId, flight.getId());
        for (Booking existing : otherBookings) {
            Flight existingFlight = existing.getFlight();
            boolean overlaps = flight.getEstDepartureTime().isBefore(existingFlight.getEstArrivalTime())
                    && flight.getEstArrivalTime().isAfter(existingFlight.getEstDepartureTime());
            if (overlaps) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scheduling conflict with existing booking");
            }
        }

        // Decrement seats
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightService.save(flight);

        // Create booking
        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now().withNano(0));
        booking.setFlight(flight);
        booking.setCustomer(user);
        Booking saved = bookingRepository.save(booking);

        writeEmailFile(saved);
        return saved.getId();
    }

    private void writeEmailFile(Booking b) {
        try {
            var fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String content = "Flight Booking Confirmation\n"
                    + "bookingDate: " + b.getBookingDate().format(fmt) + "\n"
                    + "customerFirstName: " + b.getCustomer().getFirstName() + "\n"
                    + "customerLastName: " + b.getCustomer().getLastName() + "\n"
                    + "flightNumber: " + b.getFlight().getFlightNumber() + "\n"
                    + "estDepartureTime: " + b.getFlight().getEstDepartureTime().format(fmt) + "\n"
                    + "estArrivalTime: " + b.getFlight().getEstArrivalTime().format(fmt) + "\n";
            Files.writeString(Paths.get("flight_booking_email_" + b.getId() + ".txt"), content);
        } catch (Exception ignored) {
        }
    }

    public BookingResponse getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(BookingResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }
}
