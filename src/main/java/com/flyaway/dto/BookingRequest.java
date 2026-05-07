package com.flyaway.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequest {
    @NotNull
    private Long flightId;

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
}
