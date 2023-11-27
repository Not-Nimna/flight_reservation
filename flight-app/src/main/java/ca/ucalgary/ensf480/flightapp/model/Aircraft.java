/**
 * Entity representing an aircraft.
 * 
 * This entity contains information about an aircraft used in the flight booking system, 
 * including a unique code, model, and total number of seats. It provides the basic 
 * characteristics necessary to manage and identify different aircraft within the application.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */


package ca.ucalgary.ensf480.flightapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "aircrafts")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // A unique code representing this specific aircraft.

    @Column(nullable = false)
    private String model; // The model of the aircraft, e.g., Boeing 737.

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Seat> seats = new HashSet<>(); // A set of seats in the aircraft.


    // Constructors, getters, and setters

    public Aircraft() {
    }

    public Aircraft(String code, String model, int economyRows, int seatsPerRow) {
        this.code = code;
        this.model = model;
        initializeSeats(economyRows, seatsPerRow);
    }

    // Method to initialize seats
    private void initializeSeats(int economyRows, int seatsPerRow) {
        // Create economy seats
        for (int row = 1; row <= economyRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                String seatRow = String.valueOf(row);
                String seatColumn = getSeatColumn(seatNum);
                this.addSeat(new Seat(seatRow, seatColumn, SeatClass.ECONOMY, this));
            }
        }

    }
    

    // Helper method to determine the seat column based on seat number
    private String getSeatColumn(int seatNum) {
        // This is a basic example. Adjust the logic based on your seat layout.
        return Character.toString((char) ('A' + seatNum - 1));
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    // Method to add a seat to the aircraft
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setAircraft(this);
    }
    
}
