package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;

import java.util.List;

/**
 * The {@code ShoppingCartOperations} class provides static methods for interacting with the shopping cart,
 * such as adding items, retrieving items, and managing the contents of the cart.
 */
public class ShoppingCartOperations {

    /**
     * Adds an item to the shopping cart of a specified account.
     *
     * @param accountID the ID of the account to which the item will be added.
     * @param itemID    the ID of the item to add to the shopping cart.
     */
    public static void addItemToCart(Long accountID, Long itemID) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.addItemToCart(accountID, itemID);
    }

    /**
     * Retrieves all the items in the shopping cart for a specified account.
     *
     * @param accountId the ID of the account whose shopping cart items are being retrieved.
     * @return a {@link List} of {@link ShoppingCartItem} objects representing the items in the cart.
     */
    public static List<ShoppingCartItem> getShoppingCartItems(Long accountId) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        return shoppingCartService.getShoppingCartItems(accountId);
    }

    /**
     * Saves the given shopping cart item.
     *
     * @param shoppingCartItem the {@link ShoppingCartItem} to be saved.
     */
    public static void save(ShoppingCartItem shoppingCartItem) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.save(shoppingCartItem);
    }

    /**
     * Deletes the given shopping cart item.
     *
     * @param shoppingCartItem the {@link ShoppingCartItem} to be deleted.
     */
    public static void delete(ShoppingCartItem shoppingCartItem) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.delete(shoppingCartItem);
    }

    /**
     * Deletes all items from the shopping cart for all accounts.
     */
    public static void deleteAllShoppingCartItems() {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.deleteAllShoppingCartItems();
    }
}

