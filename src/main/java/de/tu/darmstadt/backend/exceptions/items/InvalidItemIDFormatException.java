package de.tu.darmstadt.backend.exceptions.items;

import de.tu.darmstadt.dataModel.Item;

/**
 * An {@link InvalidItemIDFormatException} is a subclass of {@link ItemPropertiesException}. It is a superclass of those
 * checked exceptions that is thrown if the ID of {@link Item} is not in a valid format specified by
 * {@link Item#ITEM_ID_FORMAT}.
 */
public class InvalidItemIDFormatException extends ItemPropertiesException {

    /**
     * Constructs a new {@link InvalidItemIDFormatException} with the ID of {@link Item} as its detail message.
     * @param item_id the ID of {@link Item}
     */
    public InvalidItemIDFormatException(String item_id) {
        super("Bad item ID: " + item_id + "; The item ID should be in the format \"IT-XXXXXX\".");
    }

    /**
     * Constructs a new {@link InvalidItemIDFormatException} with a default detail message.
     */
    public InvalidItemIDFormatException() {
        super("Bad item ID. Check if the ID is in the format \"IT-XXXXXX\".");
    }

}
