package de.tu.darmstadt.backend.exceptions.accountOperation;

/**
 * An {@link IncorrectPasswordException} is a superclass of those checked exceptions is thrown if a username used to log
 * into an account is incorrect.
 */
public class IncorrectUsernameException extends AccountOperationException {

    /**
     * Constructs a new {@link IncorrectUsernameException} with {@code null} as its detail message
     */
    public IncorrectUsernameException() {
        super();
    }

    /**
     * Constructs a new {@link IncorrectUsernameException} with a specified detail message.
     * @param message the specified detail message
     */
    public IncorrectUsernameException(String message) {
        super(message);
    }

}
