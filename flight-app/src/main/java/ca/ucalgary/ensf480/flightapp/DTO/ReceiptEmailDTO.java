package ca.ucalgary.ensf480.flightapp.DTO;


public class ReceiptEmailDTO {
    private String recipient;
    private String amountPaid;
    private String paymentDate;
    // ... possibly other fields

    // Constructor
    public ReceiptEmailDTO() {
        // default constructor
    }

    // Getters
    public String getRecipient() {
        return recipient;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    // ... getters for other fields if added

    // Setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    // ... setters for other fields if added
}
