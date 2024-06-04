package de.tu.darmstadt.backend.exceptions.items;

import de.tu.darmstadt.dataModel.Item;

/**
 * A {@link NoSuchItemException} is a subclass of {@link ItemPropertiesException}. It is a superclass of those checked
 * exceptions that is thrown if an {@link Item} does not exist when performing certain operations.
 */
public class NoSuchItemException extends ItemPropertiesException {

    /**
     * Constructs a new {@link NoSuchItemException} with a specified detail message.
     * @param message the specified detail message
     */
    public NoSuchItemException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NoSuchItemException} with {@code null} as its detail message.
     */
    public NoSuchItemException() {
        super();
    }

}
