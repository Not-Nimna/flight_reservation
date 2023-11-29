
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

    public Promo(String promoDescription, Double discount) {
        this.promoDescription = promoDescription;
        this.discount = discount;
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

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
