package ca.ucalgary.ensf480.flightapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class Ensf480FlightBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ensf480FlightBookingApplication.class, args);
	}

}
