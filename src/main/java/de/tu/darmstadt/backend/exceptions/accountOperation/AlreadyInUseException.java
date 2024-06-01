package de.tu.darmstadt.backend.exceptions.accountOperation;

import de.tu.darmstadt.dataModel.Account;

/**
 * {@link AlreadyInUseException} is a subclass of {@link AccountOperationException}. It is a superclass of those
 * checked exceptions that are thrown if any data of an {@link Account} is already in use, particularly unique data.
 */
public class AlreadyInUseException extends AccountOperationException {

    /**
     * Constructs a new {@link AlreadyInUseException} with a specified detail message.
     * @param message the specified detail message
     */
    public AlreadyInUseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link AlreadyInUseException} with {@code null} as its detail message
     */
    public AlreadyInUseException() {
        super();
    }

}
