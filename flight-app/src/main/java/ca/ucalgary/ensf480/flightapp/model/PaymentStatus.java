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

/**
 * Enumeration for the status of a payment.
 *
 * Defines the different possible states for a payment transaction, such as SUCCESS, FAILED, 
 * PENDING, etc.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */
public enum PaymentStatus {
    SUCCESS,
    FAILED,
    PENDING,
    REFUNDED
}
