package de.tu.darmstadt.backend.exceptions.shoppingCart;

/**
 * A {@code ShoppingCartException} is a superclass of those checked exceptions that is thrown if any implementation or
 * operation of {@link ShoppingCartOld} is faulty.
 */
public class ShoppingCartException extends Exception {

    /**
     * Constructs a new {@link ShoppingCartException} with {@code null} as its detail message.
     */
    public ShoppingCartException() {
        this(null);
    }

    /**
     * Constructs a new {@link ShoppingCartException} with a specified detail message.
     * @param message the specified detail message
     */
    public ShoppingCartException(String message) {
        super(message);
    }

}
