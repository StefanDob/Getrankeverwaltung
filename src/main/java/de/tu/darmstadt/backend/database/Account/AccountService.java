package de.tu.darmstadt.backend.database.Account;

import de.tu.darmstadt.backend.database.Account.AccountRepository;
import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartRepository;
import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartService;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Example method to save an account
    @Transactional
    public void saveAccount(Account account) {
        try {
            Account accountNew = new Account();
            accountNew.setFirstName(account.getFirstName());
            accountNew.setLastName(account.getLastName());
            accountNew.setPassword(account.getPassword());
            accountNew.setEmail(account.getEmail());
            accountNew.setBirthDate(account.getBirthDate());
            accountNew.setPhoneNumber(account.getPhoneNumber());

            accountNew = accountRepository.save(accountNew); // Save the account
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setAccount(accountNew); // Set the saved account
            shoppingCartRepository.save(shoppingCart);

            accountNew.setShoppingCart(shoppingCart);

            accountRepository.save(accountNew);
        } catch (AccountPolicyException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> getAccountByID(final Long ID) {
        return accountRepository.findById(ID);
    }

    // New method to retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Other methods for CRUD operations or business logic can be defined here
}