/**
 * Entity representing a crew member.
 *
 * This class encapsulates details about a crew member such as their name, role, 
 * and the flights they are assigned to. It supports the management of crew 
 * assignments to flights.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */


package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "crew")
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role; // Pilot, Flight Attendant, etc.

    @ManyToMany(mappedBy = "crewMembers")
    private Set<Flight> flights = new HashSet<>(); // Flights to which this crew member is assigned.

    // Constructors, getters, and setters

    public Crew() {
    }

    public Crew(String name, String role) {
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    
}
