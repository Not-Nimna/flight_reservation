
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
        String[] promoDescriptions = {
                "Save big on this flight to london with a discount of %.2f percent!",
                "Limited-time offer: %.2f percent off on your next flight!",
                "Enjoy a special discount of %.2f percent on the flight to Singapre!",
                "Book now and get %.2f percent off on your Comfort ticket!",
                "Unbelievable savings: %.2f percent discount on fam,ily bookings!"
        };

        int randomIndex = (int) (Math.random() * promoDescriptions.length);
        this.promoDescription = String.format(promoDescriptions[randomIndex], discount * 100);
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
