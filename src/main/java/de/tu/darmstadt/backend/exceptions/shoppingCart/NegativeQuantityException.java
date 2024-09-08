package de.tu.darmstadt.backend.exceptions.shoppingCart;

import de.tu.darmstadt.dataModel.Item;

/**
 * This class is a subclass of {@link ShoppingCartException}. It is a checked exception that is thrown if the
 * quantity of an {@link Item} in a {@link ShoppingCartOld} is set to a negative value.
 */
public class NegativeQuantityException extends ShoppingCartException {

    /**
     * Constructs a new {@link NegativeQuantityException} with a specified faulty quantity of an {@link Item} in a
     * {@link ShoppingCartOld}.
     *
     * @param quantity the specified faulty quantity
     */
    public NegativeQuantityException(int quantity) {
        this("The quantity of an item cannot be negative. Actual quantity: " + quantity);
    }

    /**
     * Constructs a new {@link NegativeQuantityException} with a specified detail message.
     * @param message the specified detail message
     */
    public NegativeQuantityException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NegativeQuantityException} with {@code null} as its detail message.
     */
    public NegativeQuantityException() {
        super();
    }

}
