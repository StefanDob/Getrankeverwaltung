package de.tu.darmstadt.backend.database;

import de.tu.darmstadt.dataModel.Account;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Account> getAccountById(final String ID) {
        return accountRepository.findById(ID);
    }


    // Other methods for CRUD operations or business logic can be defined here
}
