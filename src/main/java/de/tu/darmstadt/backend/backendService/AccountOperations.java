package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.dataModel.Account;

/**
 * The class {@link AccountOperations} is used for any interaction between the frontend and the backend of this
 * project.
 */
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
}
