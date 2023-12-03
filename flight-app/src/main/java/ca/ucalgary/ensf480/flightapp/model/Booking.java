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

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bookings")
@JsonIgnoreProperties({ "user", "seat", "flight", "payment" })
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Move this here instead of having a passenger model.
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String cancellationCode; // Unique cancellation code for each booking

    @Column(nullable = false)
    private BigDecimal pricePaid; // The price paid for the booking

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight; // The flight associated with this booking

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat; // The seat associated with this booking

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // Optionally, the user associated with this booking

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment; // The payment associated with this booking

    // Constructors, getters, and setters

    public Booking() {
        this.cancellationCode = UUID.randomUUID().toString(); // Generate a unique cancellation code
    }

    public Booking(String name, String email, Flight flight, Seat seat, BigDecimal pricePaid, User user, Payment payment) {
        this();
        this.name = name;
        this.email = email;
        this.flight = flight;
        this.seat = seat;
        this.pricePaid = pricePaid;
        this.user = user;
        this.payment = payment;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment2) {
        this.payment = payment2;
    }

}
