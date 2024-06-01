package de.tu.darmstadt.backend.exceptions.accountOperation;

/**
 * An {@link IncorrectPasswordException} is a superclass of those checked exceptions is thrown if an email used to log
 * into an account is incorrect.
 */
public class IncorrectEmailException extends AccountOperationException {

    /**
     * Constructs a new {@link IncorrectEmailException} with {@code null} as its detail message
     */
    public IncorrectEmailException() {
        super();
    }

    /**
     * Constructs a new {@link IncorrectEmailException} with a specified detail message.
     * @param message the specified detail message
     */
    public IncorrectEmailException(String message) {
        super(message);
    }

}
