package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartService;
import de.tu.darmstadt.backend.database.SpringContext;

public class ShoppingCartOperations {

    public static void addItemToCart(Long accountID, Long itemID) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.addItemToCart(accountID, itemID);
    }
}
