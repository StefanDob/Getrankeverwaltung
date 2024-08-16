package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.backend.database.Account.AccountRepository;
import de.tu.darmstadt.backend.database.Item.ItemRepository;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
<<<<<<< HEAD
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
=======
>>>>>>> 365acd3c60365d4f0a705b3359ca3405c9709f78
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    public void addItemToCart(Long accountId, Long itemId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        ShoppingCart shoppingCart = account.getShoppingCart();
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        // Check if the item already exists in the shopping cart
        ShoppingCartItem existingItem = null;
        for (ShoppingCartItem cartItem : shoppingCart.getItems()) {
            if (cartItem.getItem().getITEMId().equals(itemId)) {
                existingItem = cartItem;
                break;
            }
        }

        if (existingItem != null) {
            // Update quantity of existing item
            existingItem.setQuantity(existingItem.getQuantity() + 1); // Increase quantity by 1
        } else {
            // Create a new shopping cart item if it doesn't exist
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setItem(item);
            shoppingCartItem.setQuantity(1);
            shoppingCartItem.setShoppingCart(shoppingCart);
            shoppingCart.getItems().add(shoppingCartItem);
        }

        shoppingCartRepository.save(shoppingCart);
    }

    public void removeItemFromCart(Long accountId, Long itemId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        ShoppingCart shoppingCart = account.getShoppingCart();

        shoppingCart.getItems().removeIf(item -> item.getId().equals(itemId));
        shoppingCartRepository.save(shoppingCart);
    }

    @Transactional(readOnly = true) // Mark this method as read-only for better performance
    public List<ShoppingCartItem> getShoppingCartItems(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        ShoppingCart shoppingCart = account.getShoppingCart();

        // Initialize the items list to avoid LazyInitializationException
        Hibernate.initialize(shoppingCart.getItems());

        return shoppingCart.getItems();
    }

    @Transactional
    public void save(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemRepository.save(shoppingCartItem);
    }
}
