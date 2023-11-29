
package ca.ucalgary.ensf480.flightapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "promos")
public class Promo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String promoDescription;

    @Column(nullable = false)
    private Double discount;

    // Constructors, getters, and setters

    public Promo() {
    }

    public Promo(Double discount) {
        this.discount = discount;
        setPromoDescription(discount);
    }

    public Long getId() {
        return id;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPromoDescription(Double discount) {
        this.promoDescription = String.format("This flight is %.2f percent off!", discount * 100);
    }    

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
