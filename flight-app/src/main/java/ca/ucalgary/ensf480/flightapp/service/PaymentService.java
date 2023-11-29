package ca.ucalgary.ensf480.flightapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.repository.PaymentRepository;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
      this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(PaymentDTO paymentDetails, BigDecimal price) {
      Payment payment = new Payment();

      payment.setAmount(price);
      payment.setPaymentTime(LocalDateTime.now());
      // Set other payment attributes from paymentDetails
      payment.setPaymentMethod(paymentDetails.getPaymentMethod());
      // If you store any tokenized or obfuscated payment data, set it here

      return paymentRepository.save(payment); // Save the payment record
    }
}
