/**
 * Entity representing a customer in the flight booking application.
 *
 * This class captures details about customers, including their name, email, and contact number.
 * It can represent both registered and unregistered users making flight bookings. 
 * The customer entity is linked to bookings to track the transactions made by each customer.
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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String contactNumber;

    // Optional: Reference to a User entity, if you have one for registered users
    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings = new HashSet<>(); // Bookings made by this customer

    // Constructors, getters, and setters

    public Customer() {
    }

    public Customer(String name, String email, String contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    // If you have a User entity
    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }
}

