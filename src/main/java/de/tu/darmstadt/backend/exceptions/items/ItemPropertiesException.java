package de.tu.darmstadt.backend.exceptions.items;

import de.tu.darmstadt.dataModel.Item;

/**
 * An {@link ItemPropertiesException} is a subclass of {@link Exception}. It is a superclass of those checked exceptions
 * that is thrown if a {@link Item} does not meet a {@link Item} specification.
 */
public class ItemPropertiesException extends Exception {


    /**
     * Constructs a new {@link ItemPropertiesException} with a specified detail message.
     * @param message the specified detail message
     */
    public ItemPropertiesException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ItemPropertiesException} with {@code null} as its detail message.
     */
    public ItemPropertiesException() {
        super();
    }

}
