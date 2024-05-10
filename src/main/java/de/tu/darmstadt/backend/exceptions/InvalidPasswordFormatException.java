package de.tu.darmstadt.backend.exceptions;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@link InvalidPasswordFormatException} is a superclass of those checked exceptions that is thrown if a specified
 * password (e.g., from an {@link Account}) does not meet the requirements specified by a password policy.
 */
public class InvalidPasswordFormatException extends Exception {

    /**
     * Construct a new {@link InvalidPasswordFormatException} with {@code null} as its detail message.
     */
    public InvalidPasswordFormatException() {
        super();
    }

    /**
     * Constructs a new {@link InvalidPasswordFormatException} with a specified detail message.
     * @param message the specified detail message
     */
    public InvalidPasswordFormatException(String message) {
        super(message);
    }

}
