/**
 * Payment Processing Service.
 * 
 * Manages the creation and processing of payments in the flight booking application. Simulates 
 * payment transactions and records payment details. Upon successful payment, it collaborates 
 * with the EmailService to send payment receipts to customers.
 * 
 * This service is integral to the financial transactions of the application, ensuring accurate 
 * and reliable handling of customer payments.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 2, 2023
 */

package ca.ucalgary.ensf480.flightapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentStatus;
import ca.ucalgary.ensf480.flightapp.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EmailService emailService;

    public Payment createPayment(PaymentDTO paymentDetails, BigDecimal price, Customer customer) {
      // Simulate interaction with payment provider
      String paymentToken = processPayment(paymentDetails, price);

      // Create payment object based on the response
      Payment payment = new Payment();
      payment.setAmount(price);
      payment.setPaymentTime(LocalDateTime.now());
      payment.setPaymentMethod("Card"); // Simplified representation
      payment.setPaymentStatus(paymentToken != null ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
      payment.setPaymentToken(paymentToken); // Save the payment token
      payment.setCustomer(customer);

      Payment res = paymentRepository.save(payment); // Save the payment record

      if (res != null) {
        emailService.sendReceiptEmail(payment);
      }
      return res;
    }

    private String processPayment(PaymentDTO paymentDetails, BigDecimal price) {
      // Simulate payment processing
      // In a real application, you would interact with a payment gateway here
      // For now, just simulate success/failure randomly
      boolean success = Math.random() < 0.8; // 80% chance of success

      if (success) {
        return UUID.randomUUID().toString(); // Simulate a successful payment token
      } else {
        return null; // Simulate a failed payment
      }
    }


}
