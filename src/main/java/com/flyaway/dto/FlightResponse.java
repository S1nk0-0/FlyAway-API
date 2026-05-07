package com.flyaway.dto;

import com.flyaway.entity.Flight;

import java.time.LocalDateTime;

public class FlightResponse {
    private Long id;
    private String airlineName;
    private String flightNumber;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
    private Integer availableSeats;

    public static FlightResponse from(Flight f) {
        var r = new FlightResponse();
        r.id = f.getId();
        r.airlineName = f.getAirlineName();
        r.flightNumber = f.getFlightNumber();
        r.estDepartureTime = f.getEstDepartureTime();
        r.estArrivalTime = f.getEstArrivalTime();
        r.availableSeats = f.getAvailableSeats();
        return r;
    }

    public Long getId() { return id; }
    public String getAirlineName() { return airlineName; }
    public String getFlightNumber() { return flightNumber; }
    public LocalDateTime getEstDepartureTime() { return estDepartureTime; }
    public LocalDateTime getEstArrivalTime() { return estArrivalTime; }
    public Integer getAvailableSeats() { return availableSeats; }
}
