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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;



@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"user"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true) // Foreign key in 'customers' table
    private User user;



    // Constructors, getters, and setters

    public Customer() {
    }

    public Customer(String name, String email, User user) {
        this.name = name;
        this.email = email;
        this.user = user;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

