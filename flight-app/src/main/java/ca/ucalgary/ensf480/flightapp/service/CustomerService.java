package ca.ucalgary.ensf480.flightapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.DTO.CustomerDTO;
import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;

    public Customer createOrUpdateCustomer(CustomerDTO customerDTO, User user) {
        Customer customer = customerRepository.findByEmail(customerDTO.getEmail());
        if (customer == null) {
            customer = new Customer(customerDTO.getName(), customerDTO.getEmail(), user);
        }
        customer.setName(customerDTO.getName());
        customer.setUser(user);

        return customerRepository.save(customer); // Save the new or updated customer
    }
}
