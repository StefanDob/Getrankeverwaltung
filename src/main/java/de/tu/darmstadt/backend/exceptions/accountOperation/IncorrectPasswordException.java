package de.tu.darmstadt.backend.exceptions.accountOperation;

import de.tu.darmstadt.dataModel.Account;

/**
 * A {@link IncorrectPasswordException} is a subclass of {@link AccountOperationException}. It is a superclass of those
 * checked exceptions that is thrown if a password to log into an {@link Account} with is wrong or invalid.
 */
public class IncorrectPasswordException extends AccountOperationException {

    /**
     * Constructs a new {@link IncorrectPasswordException} with {@code null} as its detail message
     */
    public IncorrectPasswordException() {
        super();
    }

    /**
     * Constructs a new {@link IncorrectPasswordException} with a specified detail message.
     * @param message the specified detail message
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }

}
