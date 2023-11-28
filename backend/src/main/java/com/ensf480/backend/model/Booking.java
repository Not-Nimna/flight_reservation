/**
 * Entity representing a booking in the flight booking application.
 *
 * This class encapsulates details about a booking, including a unique cancellation code,
 * and associations with a specific flight, seat, and customer. It also links to a payment
 * entity to handle transaction details. Designed to accommodate both registered and 
 * unregistered users.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package com.ensf480.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cancellationCode; // Unique cancellation code for each booking

    @Column(nullable = false)
    private BigDecimal price; // The price of the booking

    @Column(nullable = false)
    private Boolean isBooked;
    
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight; // The flight associated with this booking

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat; // The seat associated with this booking

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // Optionally, the user associated with this booking

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true) // Nullable for advance booking creation
    private Customer customer; // The customer who made the booking, if any

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = true) // Nullable for advance booking creation
    private Payment payment; // The payment associated with this booking, if any


    // Constructors, getters, and setters

    public Booking() {
        this.cancellationCode = UUID.randomUUID().toString(); // Generate a unique cancellation code
    }

    public Booking(Flight flight, Seat seat, BigDecimal price, User user) {
        this();
        this.isBooked = false;
        this.flight = flight;
        this.seat = seat;
        this.price = price;
        this.user = user;
        generateCancellationCode();
    }

    public void generateCancellationCode() {
        this.cancellationCode = String.valueOf(UUID.randomUUID());
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        this.isBooked = booked;
    }
    

    public String getCancellationCode() {
        return cancellationCode;
    }

    public void setCancellationCode(String cancellationCode) {
        this.cancellationCode = cancellationCode;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCustomer(Customer customer2) {
    }

    public void setPayment(Payment payment2) {
    }
}
