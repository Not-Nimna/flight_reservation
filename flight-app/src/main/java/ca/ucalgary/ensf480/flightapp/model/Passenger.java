package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public Passenger() {
    }

    public Passenger(String name, String email, Long id) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }   

    public void setId(Long id) {
        this.id = id;
    }    
}
