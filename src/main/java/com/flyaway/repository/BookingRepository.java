package com.flyaway.repository;

import com.flyaway.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.customer.id = :customerId AND b.flight.id != :flightId")
    List<Booking> findByCustomerIdExcludingFlight(@Param("customerId") Long customerId,
                                                   @Param("flightId") Long flightId);
}
