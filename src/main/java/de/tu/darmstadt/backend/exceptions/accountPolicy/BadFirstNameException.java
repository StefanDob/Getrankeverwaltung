package de.tu.darmstadt.backend.exceptions.accountPolicy;

public class BadFirstNameException extends InvalidNameFormatException {

    public BadFirstNameException() {
        super();
    }

    public BadFirstNameException(String message) {
        super(message);
    }

}
