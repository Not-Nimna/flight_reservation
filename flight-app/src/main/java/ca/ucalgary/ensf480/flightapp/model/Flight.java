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

import ca.ucalgary.ensf480.flightapp.model.Booking;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @OneToMany(mappedBy = "flight")
    private Set<Booking> bookings = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "flight_crew",
        joinColumns = @JoinColumn(name = "flight_id"),
        inverseJoinColumns = @JoinColumn(name = "crew_id")
    )
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

    public Flight(String flightNumber, Aircraft aircraft, Destination departureDestination, Destination arrivalDestination, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.aircraft = aircraft;
        this.departureDestination = departureDestination;
        this.arrivalDestination = arrivalDestination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = FlightStatus.ON_TIME;
        initializeBookings();
    }

    // Method to initialize bookings for each seat in the aircraft
    private void initializeBookings() {
        if (this.aircraft != null && this.aircraft.getSeats() != null) {
            this.aircraft.getSeats().forEach(seat -> {
                BigDecimal price = determinePriceForSeat(seat); // Implement this method based on your pricing logic
                Booking booking = new Booking(this, seat, price, null);
                booking.setCancellationCode(UUID.randomUUID().toString()); // Generate unique cancellation code
                this.bookings.add(booking);
            });
        }
    }

    private BigDecimal determinePriceForSeat(Seat seat) {
        // Example pricing logic based on seat class. In more advanced scenarios,
        // price would probably be determined each passing day.
        switch (seat.getSeatClass()) {
            case ECONOMY:
                return new BigDecimal("100.00");
            case BUSINESS:
                return new BigDecimal("200.00");
            case FIRST_CLASS:
                return new BigDecimal("400.00");
            default:
                return new BigDecimal("100.00");
        }
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
  
}
