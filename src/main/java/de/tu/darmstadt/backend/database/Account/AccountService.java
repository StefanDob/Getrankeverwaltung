package de.tu.darmstadt.backend.database.Account;

import de.tu.darmstadt.backend.database.Account.AccountRepository;
import de.tu.darmstadt.dataModel.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Example method to save an account
    public void saveAccount(Account account) {
        accountRepository.save(account);
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
