package com.flyaway.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false)
    private LocalDateTime estDepartureTime;

    @Column(nullable = false)
    private LocalDateTime estArrivalTime;

    @Column(nullable = false)
    private Integer availableSeats;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public LocalDateTime getEstDepartureTime() { return estDepartureTime; }
    public void setEstDepartureTime(LocalDateTime estDepartureTime) { this.estDepartureTime = estDepartureTime; }

    public LocalDateTime getEstArrivalTime() { return estArrivalTime; }
    public void setEstArrivalTime(LocalDateTime estArrivalTime) { this.estArrivalTime = estArrivalTime; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
