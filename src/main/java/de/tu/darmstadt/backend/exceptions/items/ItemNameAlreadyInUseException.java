package de.tu.darmstadt.backend.exceptions.items;

import de.tu.darmstadt.dataModel.Item;

/**
 * An {@link ItemNameAlreadyInUseException} is a subclass of {@link ItemPropertiesException}.
 * It is a superclass of those checked exceptions that is thrown if an {@link Item} name is already in use.
 */
public class ItemNameAlreadyInUseException extends ItemPropertiesException {

    /**
     * Constructs a new {@link ItemNameAlreadyInUseException} with a specified detail message.
     * @param message the specified detail message
     */
    public ItemNameAlreadyInUseException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ItemNameAlreadyInUseException} with {@code null} as its detail message.
     */
    public ItemNameAlreadyInUseException() {
        this(null);
    }

}
