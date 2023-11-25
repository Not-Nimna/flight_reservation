/**
 * Entity representing a travel destination.
 *
 * This class encapsulates details about travel destinations, including the name of the airport,
 * the city and country it's located in, and its unique airport code. The Destination entity 
 * is crucial for managing flight routes and booking information.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airportName; // e.g., "Calgary International Airport"

    @Column(nullable = false)
    private String city; // e.g., "Calgary"

    @Column(nullable = false)
    private String country; // e.g., "Canada"

    @Column(nullable = false, unique = true)
    private String airportCode; // e.g., "YYC"

    // Constructors, getters, and setters

    public Destination() {
    }

    public Destination(String airportName, String city, String country, String airportCode) {
        this.airportName = airportName;
        this.city = city;
        this.country = country;
        this.airportCode = airportCode;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
