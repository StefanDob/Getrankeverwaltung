package de.tu.darmstadt.backend.exceptions;

/**
 * An {@link AccountPolicyException} is a superclass of those checked exceptions that is thrown if an action
 * does not meet the requirements specified by the drink shop.
 */
public class AccountPolicyException extends Exception {

    /**
     * Constructs a new {@link AccountPolicyException} with {@code null} as its detail message.
     */
    public AccountPolicyException() {
        super();
    }

    /**
     * Constructs a new {@link AccountPolicyException} with a specified detail message.
     * @param message the specified detail message
     */
    public AccountPolicyException(String message) {
        super(message);
    }

}
