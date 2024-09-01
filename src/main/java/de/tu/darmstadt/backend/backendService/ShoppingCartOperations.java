package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;

import java.util.List;

public class ShoppingCartOperations {

    public static void addItemToCart(Long accountID, Long itemID) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.addItemToCart(accountID, itemID);
    }

    public static List<ShoppingCartItem> getShoppingCartItems(Long accountId){
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        return shoppingCartService.getShoppingCartItems(accountId);
    }

    public static void save(ShoppingCartItem shoppingCartItem) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.save(shoppingCartItem);
    }

    public static void delete(ShoppingCartItem shoppingCartItem) {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.delete(shoppingCartItem);
    }

    public static void deleteAllShoppingCartItems() {
        ShoppingCartService shoppingCartService = SpringContext.getBean(ShoppingCartService.class);
        shoppingCartService.deleteAllShoppingCartItems();
    }
}
