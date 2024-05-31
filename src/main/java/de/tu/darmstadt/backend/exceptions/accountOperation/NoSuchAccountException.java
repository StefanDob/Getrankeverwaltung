package de.tu.darmstadt.backend.exceptions.accountOperation;

import de.tu.darmstadt.dataModel.Account;

/**
 * A {@link NoSuchAccountException} is a subclass of {@link AccountOperationException}. It is a superclass of those
 * checked exceptions that is thrown if an {@link Account} tried to be accessed does not exist.
 */
public class NoSuchAccountException extends AccountOperationException {

    /**
     * Constructs a new {@link NoSuchAccountException} with {@code null} as its detail message
     */
    public NoSuchAccountException() {
        super();
    }

    /**
     * Constructs a new {@link NoSuchAccountException} with a specified detail message.
     * @param message the specified detail message
     */
    public NoSuchAccountException(String message) {
        super(message);
    }

}
