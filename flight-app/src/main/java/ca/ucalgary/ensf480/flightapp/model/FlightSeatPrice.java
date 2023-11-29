package ca.ucalgary.ensf480.flightapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "seat_prices")
public class FlightSeatPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClass seatClass;

    @Column(nullable = false)
    private BigDecimal price;

    // Constructors, getters, and setters

    public Flight getFlight()
    {
        return flight;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
