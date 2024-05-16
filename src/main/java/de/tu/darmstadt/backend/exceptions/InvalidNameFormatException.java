package de.tu.darmstadt.backend.exceptions;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@link InvalidNameFormatException} is a subclass of {@link AccountPolicyException}.
 * It is a superclass of those checked exceptions that is thrown if a name of an {@link Account} does not meet the
 * requirements specified by the {@link Account} policies.
 */
public class InvalidNameFormatException extends AccountPolicyException {

    /**
     * Constructs a new {@link InvalidNameFormatException} with {@code null} as its detail message.
     */
    public InvalidNameFormatException() {
        super();
    }

    /**
     * Constructs a new {@link InvalidNameFormatException} with a specified detail message.
     * @param message the specified detail message
     */
    public InvalidNameFormatException(String message) {
        super(message);
    }

}
