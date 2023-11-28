package ca.ucalgary.ensf480.flightapp.DTO;

import java.time.LocalDateTime;

import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.FlightStatus;

public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String aircraftCode; // You might want to show only the code of the aircraft, not all its details
    private String departureDestination;
    private String arrivalDestination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightStatus status;

    

    public static FlightDTO fromFlight(Flight flight) {
      FlightDTO dto = new FlightDTO();
      dto.id = flight.getId();
      dto.flightNumber = flight.getFlightNumber();
      dto.aircraftCode = flight.getAircraft() != null ? flight.getAircraft().getCode() : null;
      dto.departureDestination = flight.getDepartureDestination() != null ? flight.getDepartureDestination().getAirportName() : null;
      dto.arrivalDestination = flight.getArrivalDestination() != null ? flight.getArrivalDestination().getAirportName() : null;
      dto.departureTime = flight.getDepartureTime();
      dto.arrivalTime = flight.getArrivalTime();
      dto.status = flight.getStatus();
      return dto;
  }

  // Getters
  public Long getId() {
    return id;
  }

  public String getFlightNumber() {
      return flightNumber;
  }

  public String getAircraftCode() {
      return aircraftCode;
  }

  public String getDepartureDestination() {
      return departureDestination;
  }

  public String getArrivalDestination() {
      return arrivalDestination;
  }

  public LocalDateTime getDepartureTime() {
      return departureTime;
  }

  public LocalDateTime getArrivalTime() {
      return arrivalTime;
  }

  public FlightStatus getStatus() {
      return status;
  }

  // Setters
  public void setId(Long id) {
      this.id = id;
  }

  public void setFlightNumber(String flightNumber) {
      this.flightNumber = flightNumber;
  }

  public void setAircraftCode(String aircraftCode) {
      this.aircraftCode = aircraftCode;
  }

  public void setDepartureDestination(String departureDestination) {
      this.departureDestination = departureDestination;
  }

  public void setArrivalDestination(String arrivalDestination) {
      this.arrivalDestination = arrivalDestination;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
      this.departureTime = departureTime;
  }

  public void setArrivalTime(LocalDateTime arrivalTime) {
      this.arrivalTime = arrivalTime;
  }

  public void setStatus(FlightStatus status) {
      this.status = status;
  }

}
