package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.database.AccountService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

/**
 * The class {@link AccountOperations} is used for any interaction between the frontend and the backend of this
 * project.
 */
@Component
public class AccountOperations {



    /**
     * This static method is used to create a new {@link Account} from the frontend.
     * The data inserted in the frontend is passed to the backend via this method.
     * The specified {@link Account} parameter contains data to be stored in the database.
     *
     * @param account the specified {@link Account} containing all information upon creation.
     */
    public static void createAccount(Account account){
        // TODO: make some logic that writes the parameter to the database

        AccountService accountService = SpringContext.getBean(AccountService.class);
        accountService.saveAccount(account);
    }

    public static Account getAccountByUserName(String userName, char[] password){
        // TODO: make some logic that retrieves the account information, also first check wether the password is right
        return checkForTestUser(userName);

    }

    /**
     * this is a test method until a working implementation is established
     */
    private static Account checkForTestUser(String userName) {
        if(userName.equals("test")){
            try {
                return new Account("dobreastefan68@gmail.com", "hello123", "Stefan", "Dobrea",
                        LocalDate.of(2002,12,9), null, AccountStatus.STANDARD, 50);
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("method getAccountByUserName not jet implemented");
    }


    public static Account getAccountByEmail(String mail) {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        Optional<Account> accountOptional = accountService.getAccountByEmail(mail);
        return accountOptional.orElse(null);
    }
}
