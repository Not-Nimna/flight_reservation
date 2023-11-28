package ca.ucalgary.ensf480.flightapp.DTO;

import ca.ucalgary.ensf480.flightapp.model.Seat;

public class SeatBookingDTO {
    private final String seatNumber;
    private final boolean isBooked;

    public SeatBookingDTO(Seat seat, boolean isBooked) {
        this.seatNumber = seat.getSeatNumber();
        this.isBooked = isBooked;
    }

    // Getters
    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }
}
