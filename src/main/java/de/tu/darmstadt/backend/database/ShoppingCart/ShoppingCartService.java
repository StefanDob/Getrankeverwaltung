package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.backend.database.Account.AccountRepository;
import de.tu.darmstadt.backend.database.Item.ItemRepository;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public ShoppingCart createCartForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        ShoppingCart shoppingCart = new ShoppingCart(account);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart addItemToCart(Long accountId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found")));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        shoppingCart.addItem(item);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart getCartByAccount(Long accountId) {
        return shoppingCartRepository.findByAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found")));
    }
}
