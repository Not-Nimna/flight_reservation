/**
 * Entity representing a payment transaction.
 *
 * This class captures details about payments, including the amount, date, status, and 
 * the associated booking. It is crucial for managing financial transactions related 
 * to bookings in the flight booking application.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */


package com.ensf480.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount; // The amount of the payment

    @Column(nullable = false)
    private LocalDateTime paymentDate; // The date and time when the payment was made

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // The status of the payment (e.g., SUCCESS, FAILED)

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // The customer who made the payment

    @Column(nullable = false)
    private LocalDateTime paymentTime; // The time of the payment


    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking; // The booking associated with this payment

    // Constructors, getters, and setters

    public Payment() {
    }

    public Payment(BigDecimal amount, LocalDateTime paymentDate, PaymentStatus status, Booking booking) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
        this.booking = booking;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    // TODO:
    public void setPaymentTime(LocalDateTime now) {
    }

    public void setPaymentMethod(Object paymentMethod) {
    }

    public Payment orElse(Object object) {
        return null;
      }
}
