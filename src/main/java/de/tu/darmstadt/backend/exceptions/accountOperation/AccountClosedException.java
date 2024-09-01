package de.tu.darmstadt.backend.exceptions.accountOperation;

/**
 * Exception for when the account is closed
 */
public class AccountClosedException extends AccountOperationException {


    public AccountClosedException() {
        super();
    }


    public AccountClosedException(String message) {
        super(message);
    }

}
