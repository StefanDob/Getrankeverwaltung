package de.tu.darmstadt.backend.database;

import de.tu.darmstadt.dataModel.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseAccess {



    @Autowired
    private AccountService accountService; // Inject AccountService

    public Optional<Account> findByEmail(String email) {
        return accountService.getAccountByEmail(email);
    }


}
