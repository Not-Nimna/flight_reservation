/**
 * Enum definition for user types within the application.
 * 
 * This enum represents different types of users like User, Agent, and Admin.
 * Each user type is associated with an integer value for database storage.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.model;

public enum UserType {
    USER(0),
    AGENT(1),
    ADMIN(2);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserType fromValue(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UserType value: " + value);
    }
}
