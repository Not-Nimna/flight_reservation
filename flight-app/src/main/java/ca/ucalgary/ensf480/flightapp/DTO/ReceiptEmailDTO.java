package ca.ucalgary.ensf480.flightapp.DTO;


public class ReceiptEmailDTO {
    private String name;
    private String email;
    private String amountPaid;
    private String paymentDate;

    // Constructor
    public ReceiptEmailDTO() {
    }

    public ReceiptEmailDTO(String name, String email, String amountPaid, String paymentDate) {
        this.name = name;
        this.email = email;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    // ... getters for other fields if added


    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    // ... setters for other fields if added
}
