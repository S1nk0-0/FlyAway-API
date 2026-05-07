package com.flyaway.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class FlightCreateRequest {
    @NotBlank
    private String airlineName;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$")
    private String flightNumber;

    @NotBlank
    private String estDepartureTime;

    @NotBlank
    private String estArrivalTime;

    @NotNull
    @Min(1)
    private Integer availableSeats;

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getEstDepartureTime() { return estDepartureTime; }
    public void setEstDepartureTime(String estDepartureTime) { this.estDepartureTime = estDepartureTime; }

    public String getEstArrivalTime() { return estArrivalTime; }
    public void setEstArrivalTime(String estArrivalTime) { this.estArrivalTime = estArrivalTime; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
