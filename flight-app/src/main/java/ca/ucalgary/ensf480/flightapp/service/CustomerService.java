package ca.ucalgary.ensf480.flightapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentDetails;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    public Customer createOrUpdateCustomer(String customerEmail, Long userId) {
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            customer = new Customer();
            customer.setEmail(customerEmail);
        }

        if (userId != null) {
          userService.findById(userId).ifPresent(customer::setUser);
        }
    
        customer.setEmail(customerEmail);
        return customerRepository.save(customer); // Save the new or updated customer
    }
}
