package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.backend.database.Account.AccountRepository;
import de.tu.darmstadt.backend.database.Item.ItemRepository;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
import de.tu.darmstadt.frontend.account.SessionManagement;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<ShoppingCartItem> getShoppingCartItems(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        ShoppingCart shoppingCart = account.getShoppingCart();

        // Initialize the items list to avoid LazyInitializationException
        Hibernate.initialize(shoppingCart.getItems());

        // Filter out null items
        List<ShoppingCartItem> items = shoppingCart.getItems().stream()
                .filter(item -> item.getItem() != null)
                .collect(Collectors.toList());

        // Remove null items from the shopping cart in the database
        shoppingCart.getItems().removeIf(item -> item.getItem() == null);
        shoppingCartRepository.save(shoppingCart);

        return items;
    }


    @Transactional
    public void save(ShoppingCartItem shoppingCartItem) {
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Transactional
    public void delete(ShoppingCartItem shoppingCartItem) {
        ShoppingCart shoppingCart = shoppingCartItem.getShoppingCart();
        shoppingCart.getItems().remove(shoppingCartItem);
        // This triggers the orphanRemoval
        shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void deleteAllShoppingCartItems() {
        ShoppingCart shoppingCart = SessionManagement.getAccount().getShoppingCart();
        // Clear the items list, which removes all items from the shopping cart
        shoppingCart.getItems().clear();

        // Save the shopping cart to apply the changes and trigger orphanRemoval
        shoppingCartRepository.save(shoppingCart);
    }
}
