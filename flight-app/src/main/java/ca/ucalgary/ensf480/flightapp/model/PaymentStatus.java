/**
 * Enumeration for the status of a payment transaction.
 *
 * This enum defines various states of a payment associated with a booking, 
 * such as SUCCESS, FAILED, PENDING, and REFUNDED. It provides a standardized 
 * way to represent and manage the status of payment transactions in the flight 
 * booking system.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

public enum PaymentStatus {
    SUCCESS(0),
    FAILED(1),
    PENDING(2),
    REFUNDED(3);

    private final int value;

    PaymentStatus(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static PaymentStatus fromValue(int value) {
      for (PaymentStatus type : PaymentStatus.values()) {
          if (type.getValue() == value) {
              return type;
          }
      }
      throw new IllegalArgumentException("Unknown PaymentStatus value: " + value);
  }
}
