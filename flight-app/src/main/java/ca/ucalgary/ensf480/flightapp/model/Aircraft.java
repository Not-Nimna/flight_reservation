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

    @Column(nullable = false)
    private int totalSeats; // The total number of seats available in the aircraft.

    // Constructors, getters, and setters

    public Aircraft() {
    }

    public Aircraft(String code, String model, int totalSeats) {
        this.code = code;
        this.model = model;
        this.totalSeats = totalSeats;
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

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

}
