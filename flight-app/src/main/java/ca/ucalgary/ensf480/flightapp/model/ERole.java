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

public enum ERole {
    ROLE_USER(0),
    ROLE_AGENT(1),
    ROLE_ADMIN(2);

    private final int value;

    ERole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ERole fromValue(int value) {
        for (ERole type : ERole.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UserType value: " + value);
    }
}
