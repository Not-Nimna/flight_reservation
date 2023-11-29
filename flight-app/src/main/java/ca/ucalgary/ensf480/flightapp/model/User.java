/**
 * Entity model for users of the flight booking application.
 * 
 * This class represents a User with properties like email, password, and userType.
 * It includes methods for password encryption and user type checks.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(name = "destination")
    private String destination;

    @Column(name = "is_current")
    private Boolean isCurrent;

    // Constructors, Getters, and Setters

    public User() {
    }

    public User(String email, String password, UserType userType) {
        this.email = email;
        setPassword(password);
        this.userType = userType;
        this.destination = "";
        this.isCurrent = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    // Use Spring Security's BCryptPasswordEncoder to set the password securely
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    // Custom methods for user type checks can be helpful

    public boolean isAdmin() {
        return this.userType == UserType.ADMIN;
    }

    public boolean isAgent() {
        return this.userType == UserType.AGENT;
    }

    public boolean isUser() {
        return this.userType == UserType.USER;
    }

    public User orElse(Object object) {
        return null;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

}
