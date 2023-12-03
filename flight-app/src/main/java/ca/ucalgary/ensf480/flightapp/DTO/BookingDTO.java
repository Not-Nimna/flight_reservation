 /** 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.DTO;

import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.model.SeatClass;

public class BookingDTO {

  private String name;
  private String email;
  private Long seatId;
  private String seatNumber;
  private SeatClass seatClass;
  private String cancellationCode;
  private FlightDTO flightDTO;

  public BookingDTO() {
  }

  public BookingDTO(Booking booking) {
    this.name = booking.getName();
    this.email = booking.getEmail();
    this.cancellationCode = booking.getCancellationCode();

    Seat seat = booking.getSeat();
    this.seatId = seat.getId();
    this.seatNumber = seat.getSeatNumber();
    this.seatClass = seat.getSeatClass();

    this.flightDTO = FlightDTO.fromFlight(booking.getFlight());
  }

  // Getters
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Long getSeatId() {
    return seatId;
  }

  public String getSeatNumber() {
    return seatNumber;
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }

  public String getCancellationCode() {
    return cancellationCode;
  }

  public FlightDTO getFlightDTO() {
    return flightDTO;
  }

  // Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSeatId(Long seatId) {
    this.seatId = seatId;
  }

  public void setSeatNumber(String seatNumber) {
    this.seatNumber = seatNumber;
  }

  public void setSeatClass(SeatClass seatClass) {
    this.seatClass = seatClass;
  }

  public void setCancellationCode(String cancellationCode) {
    this.cancellationCode = cancellationCode;
  }

  public void setFlightDTO(FlightDTO flightDTO) {
    this.flightDTO = flightDTO;
  }

  // Additional methods can be added here

}
