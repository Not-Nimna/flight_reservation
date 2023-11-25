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


package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cancellationCode; // Unique cancellation code for each booking

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight; // The flight associated with this booking

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat; // The seat associated with this booking

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // The customer who made the booking

    @OneToOne(mappedBy = "booking")
    private Payment payment; // The payment associated with this booking

    // Constructors, getters, and setters

    public Booking() {
        this.cancellationCode = UUID.randomUUID().toString(); // Generate a unique cancellation code
    }

    public Booking(Flight flight, Seat seat, Customer customer) {
        this();
        this.flight = flight;
        this.seat = seat;
        this.customer = customer;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
