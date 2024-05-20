package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.database.DatabaseAccess;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.dataModel.Account;
import org.springframework.stereotype.Component;

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

        throw new RuntimeException(" method createAccount not yet implemented");
    }

    public static Account getAccountByUserName(String userName, char[] password){
        // TODO: make some logic that retrieves the account information, also first check wether the password is right
        throw new RuntimeException("method getAccountByUserName not jet implemented");
    }


    public static Account getAccountByEmail(String mail) {
        DatabaseAccess databaseAccess = SpringContext.getBean(DatabaseAccess.class);
        Optional<Account> accountOptional = databaseAccess.findByEmail(mail);
        return accountOptional.orElse(null);
    }
}
