/**
 * Entity representing a seat in an aircraft.
 *
 * This class encapsulates details about a seat such as its number, class (economy, business, etc.), 
 * and its booking status. It also maintains a relationship with the Aircraft entity to which 
 * the seat belongs.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */


package ca.ucalgary.ensf480.flightapp.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
@JsonIgnoreProperties("aircraft")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber; // A unique identifier for the seat within an aircraft, e.g., "12A".

    @Column(nullable = false)
    private String seatRow;

    @Column(nullable = false)
    private String seatColumn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClass seatClass; // Class of the seat (ECONOMY, BUSINESS, etc.)

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft; // The aircraft to which this seat belongs.

    // Constructors, getters, and setters

    public Seat() {
    }

    public Seat(String seatRow, String seatColumn, SeatClass seatClass, Aircraft aircraft) {
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatNumber = seatRow + seatColumn;
        this.seatClass = seatClass;
        this.aircraft = aircraft;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getSeatColumn() {
        return seatColumn;
    }
}
