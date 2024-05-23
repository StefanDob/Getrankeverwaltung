package de.tu.darmstadt.backend.exceptions.accountPolicy;

public class BadLastNameException extends InvalidNameFormatException {

    public BadLastNameException() {
        super();
    }

    public BadLastNameException(String message) {
        super(message);
    }

}
