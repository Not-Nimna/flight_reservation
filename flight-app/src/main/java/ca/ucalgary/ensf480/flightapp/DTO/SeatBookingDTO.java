 /** 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.DTO;

import java.math.BigDecimal;

import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.model.SeatClass;

public class SeatBookingDTO {

    private final Long seatId;
    private final String seatNumber;
    private final String seatRow;
    private final String seatColumn;
    private final SeatClass seatClass;
    private final boolean isBooked;
    private BigDecimal price;

    public SeatBookingDTO(Seat seat, boolean isBooked, BigDecimal price) {
        this.seatId = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatRow = seat.getSeatRow();
        this.seatColumn = seat.getSeatColumn();
        this.seatClass = seat.getSeatClass();
        this.isBooked = isBooked;
        this.price = price;
    }

    // Getters
    public Long getSeatId() {
        return this.seatId;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getSeatColumn() { 
        return seatColumn;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
