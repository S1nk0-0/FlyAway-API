package com.flyaway.dto;

import com.flyaway.entity.Booking;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private LocalDateTime bookingDate;
    private Long flightId;
    private String flightNumber;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;

    public static BookingResponse from(Booking b) {
        var r = new BookingResponse();
        r.id = b.getId();
        r.bookingDate = b.getBookingDate();
        r.flightId = b.getFlight().getId();
        r.flightNumber = b.getFlight().getFlightNumber();
        r.customerId = b.getCustomer().getId();
        r.customerFirstName = b.getCustomer().getFirstName();
        r.customerLastName = b.getCustomer().getLastName();
        r.estDepartureTime = b.getFlight().getEstDepartureTime();
        r.estArrivalTime = b.getFlight().getEstArrivalTime();
        return r;
    }

    public Long getId() { return id; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public Long getFlightId() { return flightId; }
    public String getFlightNumber() { return flightNumber; }
    public Long getCustomerId() { return customerId; }
    public String getCustomerFirstName() { return customerFirstName; }
    public String getCustomerLastName() { return customerLastName; }
    public LocalDateTime getEstDepartureTime() { return estDepartureTime; }
    public LocalDateTime getEstArrivalTime() { return estArrivalTime; }
}
