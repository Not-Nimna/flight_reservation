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


package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "payments")
@JsonIgnoreProperties({"customer"})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount; // The amount of the payment

    @Column(nullable = false)
    private LocalDateTime paymentTime; // The date and time when the payment was made

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // The status of the payment (e.g., SUCCESS, FAILED)

    @Column(nullable = true)
    private String paymentMethod;

    @Column(nullable = true)
    private String paymentToken; // Token representing the transaction

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // The customer who made the payment


    // Constructors, getters, and setters

    public Payment() {
        // Default constructor
    }

    public Payment(BigDecimal amount, 
            LocalDateTime paymentTime, 
            PaymentStatus status, 
            Booking booking, 
            String paymentToken,
            String paymentMethod) {
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.status = status;
        this.paymentToken = paymentToken;
        this.paymentMethod = paymentMethod;
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

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentStatus getPaymentStatus() {
        return status;
    }

    public void setPaymentStatus(PaymentStatus status) {
        this.status = status;
    }


    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
