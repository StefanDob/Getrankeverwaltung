package de.tu.darmstadt.backend.exceptions.accountPolicy;

import de.tu.darmstadt.dataModel.Account;

/**
 * An {@code EmailAlreadyInUseException} is a subclass of {@link AccountPolicyException}.
 * It is a superclass of those checked exceptions that is thrown if an email from {@link Account} is already existing.
 */
public class EmailAlreadyInUseException extends AccountPolicyException {

    /**
     * Constructs a new {@link EmailAlreadyInUseException} with {@code null} as its detail message.
     */
    public EmailAlreadyInUseException() {
        super();
    }

    /**
     * Constructs a new {@link EmailAlreadyInUseException} with a specified detail message.
     * @param message the specified detail message
     */
    public EmailAlreadyInUseException(final String message) {
        super(message);
    }

}
