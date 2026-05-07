package com.flyaway.service;

import com.flyaway.dto.FlightCreateRequest;
import com.flyaway.dto.FlightResponse;
import com.flyaway.entity.Flight;
import com.flyaway.repository.FlightRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional
    public Long createFlight(FlightCreateRequest req) {
        if (flightRepository.existsByFlightNumber(req.getFlightNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight number already exists");
        }
        Flight flight = new Flight();
        flight.setAirlineName(req.getAirlineName());
        flight.setFlightNumber(req.getFlightNumber());
        flight.setEstDepartureTime(parseISO(req.getEstDepartureTime()));
        flight.setEstArrivalTime(parseISO(req.getEstArrivalTime()));
        flight.setAvailableSeats(req.getAvailableSeats());
        return flightRepository.save(flight).getId();
    }

    public FlightResponse getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(FlightResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    public List<FlightResponse> searchFlights(String flightNumber, String airlineName,
                                               String dateFrom, String dateTo) {
        Specification<Flight> spec = Specification.where(null);

        if (flightNumber != null && !flightNumber.isBlank()) {
            spec = spec.and((root, q, cb) ->
                cb.like(root.get("flightNumber"), "%" + flightNumber + "%"));
        }
        if (airlineName != null && !airlineName.isBlank()) {
            spec = spec.and((root, q, cb) ->
                cb.like(root.get("airlineName"), "%" + airlineName + "%"));
        }
        if (dateFrom != null && !dateFrom.isBlank()) {
            LocalDateTime from = parseISO(dateFrom);
            spec = spec.and((root, q, cb) ->
                cb.greaterThanOrEqualTo(root.get("estDepartureTime"), from));
        }
        if (dateTo != null && !dateTo.isBlank()) {
            LocalDateTime to = parseISO(dateTo);
            spec = spec.and((root, q, cb) ->
                cb.lessThanOrEqualTo(root.get("estDepartureTime"), to));
        }

        return flightRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(FlightResponse::from)
                .toList();
    }

    public Flight getFlightEntityById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @Transactional
    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    private LocalDateTime parseISO(String s) {
        try {
            return Instant.parse(s).atZone(ZoneId.of("UTC")).toLocalDateTime();
        } catch (Exception e) {
            try {
                return LocalDateTime.parse(s);
            } catch (Exception e2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid datetime format: " + s);
            }
        }
    }
}
