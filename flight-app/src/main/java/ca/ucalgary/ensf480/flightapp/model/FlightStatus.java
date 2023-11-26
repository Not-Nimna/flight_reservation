/**
 * Enumeration for different statuses of a flight.
 *
 * This enum defines various possible statuses for a flight, such as ON_TIME, DELAYED, 
 * CANCELLED, and BOARDING. It provides a standardized way to represent the state of 
 * a flight in the system.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

public enum FlightStatus {
    ON_TIME(0),
    DELAYED(1),
    CANCELLED(2),
    BOARDING(3);

    private final int value;

    FlightStatus(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static FlightStatus fromValue(int value) {
      for (FlightStatus type : FlightStatus.values()) {
          if (type.getValue() == value) {
              return type;
          }
      }
      throw new IllegalArgumentException("Unknown FlightStatus value: " + value);
  }
}
