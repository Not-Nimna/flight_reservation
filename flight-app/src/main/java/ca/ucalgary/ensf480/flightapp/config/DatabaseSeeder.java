package ca.ucalgary.ensf480.flightapp.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ucalgary.ensf480.flightapp.model.*;
import ca.ucalgary.ensf480.flightapp.repository.*;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    // @Autowired
    // private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private PromoRepository promoRepository;

    @PostConstruct
    public void seedDatabase() {

        // Create some destinations
        ArrayList<Destination> destinations = new ArrayList<Destination>();
        destinations.add(new Destination("Calgary Airport", "Calgary", "Canada", "YYC"));
        destinations.add(new Destination("Toronto Pearson International Airport", "Toronto", "Canada", "YYZ"));
        destinations.add(new Destination("Vancouver International Airport", "Vancouver", "Canada", "YVR"));
        destinations.add(new Destination("Montréal–Trudeau International Airport", "Montreal", "Canada", "YUL"));
        destinations.add(new Destination("John F. Kennedy International Airport", "New York", "USA", "JFK"));
        destinations.add(new Destination("Heathrow Airport", "London", "United Kingdom", "LHR"));
        destinations.add(new Destination("Haneda Airport", "Tokyo", "Japan", "HND"));
        destinations.add(new Destination("Sydney Kingsford Smith Airport", "Sydney", "Australia", "SYD"));
        destinations.add(new Destination("Charles de Gaulle Airport", "Paris", "France", "CDG"));
        destinations.add(new Destination("Dubai International Airport", "Dubai", "United Arab Emirates", "DXB"));
        destinations.add(new Destination("Singapore Changi Airport", "Singapore", "Singapore", "SIN"));
        destinations.add(new Destination("Los Angeles International Airport", "Los Angeles", "USA", "LAX"));
        destinations.add(new Destination("Beijing Capital International Airport", "Beijing", "China", "PEK"));
        destinations.add(new Destination("Rome Fiumicino Airport", "Rome", "Italy", "FCO"));

        destinations.forEach(destination -> destinationRepository.save(destination));

        ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();

        // Define the seat layout for each aircraft
        Map<String, SeatClass> layout1 = new HashMap<>();
        // Assuming first 5 rows are BUSINESS_CLASS, next 10 are COMFORT, and rest ORDINARY
        for (int i = 1; i <= 30; i++) {
            for (char j = 'A'; j <= 'F'; j++) {
                String position = i + String.valueOf(j);
                SeatClass seatClass = i <= 5 ? SeatClass.BUSINESS_CLASS : (i <= 15 ? SeatClass.COMFORT : SeatClass.ORDINARY);
                layout1.put(position, seatClass);
            }
        }

        // Adding some example aircraft with their configurations

        aircrafts.add(new Aircraft("A1", "Boeing 737", layout1)); 
        aircrafts.add(new Aircraft("A2", "Airbus A320", layout1)); 
        aircrafts.add(new Aircraft("A3", "Boeing 777", layout1));
        aircrafts.add(new Aircraft("A4", "Boeing A380", layout1)); 
        aircrafts.add(new Aircraft("A5", "Airbus A340", layout1)); 

        aircrafts.forEach(aircraft -> aircraftRepository.save(aircraft));

        LocalDateTime departure1 = LocalDateTime.of(2023, 12, 1, 6, 30);
        LocalDateTime arrival1 = LocalDateTime.of(2023, 12, 1, 9, 45);

        LocalDateTime departure2 = LocalDateTime.of(2023, 12, 2, 8, 0);
        LocalDateTime arrival2 = LocalDateTime.of(2023, 12, 2, 12, 30);

        LocalDateTime departure3 = LocalDateTime.of(2023, 12, 3, 14, 15);
        LocalDateTime arrival3 = LocalDateTime.of(2023, 12, 3, 18, 0);

        LocalDateTime departure4 = LocalDateTime.of(2023, 12, 4, 17, 0);
        LocalDateTime arrival4 = LocalDateTime.of(2023, 12, 4, 21, 30);

        LocalDateTime departure5 = LocalDateTime.of(2023, 12, 5, 20, 30);
        LocalDateTime arrival5 = LocalDateTime.of(2023, 12, 5, 23, 45);

        BigDecimal ordinaryPrice = new BigDecimal(100.00);
        BigDecimal comfortPrice = new BigDecimal(200.00);
        BigDecimal businessPrice = new BigDecimal(400.00);

        Promo promo = new Promo(0.2);
        promoRepository.save(promo);


        ArrayList<Flight> flights = new ArrayList<Flight>();
        // Add flights to the flights array
        flights.add(new Flight("F001", aircrafts.get(0), destinations.get(0), destinations.get(5), departure1, arrival1, ordinaryPrice, comfortPrice, businessPrice, null));
        flights.add(new Flight("F002", aircrafts.get(1), destinations.get(1), destinations.get(6), departure2, arrival2, ordinaryPrice, comfortPrice, businessPrice, null));
        flights.add(new Flight("F003", aircrafts.get(2), destinations.get(2), destinations.get(7), departure3, arrival3, ordinaryPrice, comfortPrice, businessPrice, null));
        flights.add(new Flight("F004", aircrafts.get(3), destinations.get(3), destinations.get(8), departure4, arrival4, ordinaryPrice, comfortPrice, businessPrice, null));
        flights.add(new Flight("F005", aircrafts.get(4), destinations.get(4), destinations.get(9), departure5, arrival5, ordinaryPrice, comfortPrice, businessPrice, promo));

        flights.forEach(flight -> flightRepository.save(flight));

    }
}
