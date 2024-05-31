package de.tu.darmstadt.backend.exceptions.accountOperation;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@link AccountOperationException} is a superclass of those checked exceptions is thrown if any operation
 * related to {@link Account} access is faulty.
 */
public class AccountOperationException extends Exception {

    /**
     * Constructs a new {@link AccountOperationException} with a specified detail message.
     * @param message the specified detail message
     */
    public AccountOperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link AccountOperationException} with {@code null} as its detail message
     */
    public AccountOperationException() {
        super();
    }

}
