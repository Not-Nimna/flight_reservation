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
import java.util.Map;
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

    // Default constructor
    protected Aircraft() {
        // Used by Hibernate
    }

    // Constructor with seat layout
    public Aircraft(String code, String model, Map<String, SeatClass> seatLayout) {
        this.code = code;
        this.model = model;
        initializeSeats(seatLayout);
    }

    // Initialize seats based on the provided layout
    private void initializeSeats(Map<String, SeatClass> seatLayout) {
        seatLayout.forEach((position, seatClass) -> {
            String seatRow = extractRow(position);
            String seatColumn = extractColumn(position);
            this.addSeat(new Seat(seatRow, seatColumn, seatClass, this));
        });
    }

    // Helper methods to extract row and column from the position
    private String extractRow(String position) {
        // Assuming the format "1A", "2B", etc., where the row is the numeric part
        return position.replaceAll("[^0-9]", "");
    }

    private String extractColumn(String position) {
        // Assuming the format "1A", "2B", etc., where the column is the letter part
        return position.replaceAll("[^A-Za-z]", "");
    }

    // Method to add a seat to the aircraft
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setAircraft(this);
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
}
