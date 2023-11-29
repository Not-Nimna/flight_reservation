package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Passenger() {
    }

    public Passenger(String name) {
        this.name = name;
    }

    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }    
}
