/**
 * Entity representing a flight.
 *
 * This class encapsulates comprehensive details about a flight, including its unique number,
 * aircraft, crew, departure and arrival destinations, times, and estimated flight duration. 
 * It also includes the status of the flight for real-time updates. The class links with other 
 * entities like Booking, Crew, Aircraft, and Destination to provide detailed flight information.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {

    @OneToOne
    @JoinColumn(name = "promo_id")
    private Promo promo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FlightSeatPrice> seatPrices = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "flight_crew", joinColumns = @JoinColumn(name = "flight_id"), inverseJoinColumns = @JoinColumn(name = "crew_id"))
    private Set<Crew> crewMembers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "departure_destination_id", nullable = false)
    private Destination departureDestination;

    @ManyToOne
    @JoinColumn(name = "arrival_destination_id", nullable = false)
    private Destination arrivalDestination;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status;

    // Constructors, getters, and setters

    public Flight() {
    }

    // Constructor with seat prices
    public Flight(String flightNumber, Aircraft aircraft, Destination departureDestination, 
                  Destination arrivalDestination, LocalDateTime departureTime, 
                  LocalDateTime arrivalTime, BigDecimal ordinaryPrice, 
        
        this.flightNumber = flightNumber;
        this.aircraft = aircraft;
        this.departureDestination = departureDestination;
        this.arrivalDestination = arrivalDestination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = FlightStatus.ON_TIME;
        this.promo = promo;

        // Initialize seat prices
        addSeatPrice(SeatClass.ORDINARY, ordinaryPrice);
        addSeatPrice(SeatClass.COMFORT, comfortPrice);
        addSeatPrice(SeatClass.BUSINESS_CLASS, businessPrice);
    }

    private void addSeatPrice(SeatClass seatClass, BigDecimal price) {
        FlightSeatPrice flightSeatPrice = new FlightSeatPrice();
        flightSeatPrice.setSeatClass(seatClass);
        flightSeatPrice.setPrice(price);
        flightSeatPrice.setFlight(this); // Link the price to this flight
        this.seatPrices.add(flightSeatPrice);
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Crew> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(Set<Crew> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public Destination getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(Destination departureDestination) {
        this.departureDestination = departureDestination;
    }

    public Destination getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(Destination arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    // Add getters and setters for seatPrices
    public Set<FlightSeatPrice> getSeatPrices() {
        return seatPrices;
    }

    public void setSeatPrices(Set<FlightSeatPrice> seatPrices) {
        this.seatPrices = seatPrices;
    }

    public Promo getPromo() {
        return promo;
    }
    public void setPromo(Promo promo) {
        this.promo = promo;
    }
}
