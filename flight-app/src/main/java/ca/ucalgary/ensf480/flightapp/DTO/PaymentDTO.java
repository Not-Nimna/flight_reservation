 /** 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.DTO;

public class PaymentDTO {

    private String cardNumber;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardCvv;

    public PaymentDTO() {
        // Default constructor
    }

    // Constructor with parameters
    public PaymentDTO(String cardNumber, String cardExpiryMonth, String cardExpiryYear, String cardCvv) {
        this.cardNumber = cardNumber;
        this.cardExpiryMonth = cardExpiryMonth;
        this.cardExpiryYear = cardExpiryYear;
        this.cardCvv = cardCvv;
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    // You might want to add additional methods or logic, such as validation, as needed
}
