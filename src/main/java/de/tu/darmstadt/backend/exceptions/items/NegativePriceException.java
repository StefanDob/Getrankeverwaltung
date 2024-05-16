package de.tu.darmstadt.backend.exceptions.items;

import de.tu.darmstadt.dataModel.Item;

/**
 * A {@link NegativePriceException} is a subclass of {@link ItemPropertiesException}. It is a superclass of those
 * checked exceptions that is thrown if the price of an {@link Item} is negative.
 */
public class NegativePriceException extends ItemPropertiesException {

    /**
     * Constructs a new {@link NegativePriceException} with a specified invalid price value. It has a detail message
     * in the following format:
     * <p>
     *     {@code "The price is negative: <price>"}
     *
     * @param price the specified price
     */
    public NegativePriceException(double price) {
        super("The price is negative: " + price);
    }

}
