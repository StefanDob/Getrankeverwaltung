package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.database.AccountService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.accountOperation.AccountOperationException;
import de.tu.darmstadt.backend.exceptions.accountOperation.IncorrectEmailException;
import de.tu.darmstadt.backend.exceptions.accountOperation.NoSuchAccountException;
import de.tu.darmstadt.backend.exceptions.accountOperation.IncorrectPasswordException;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

import static de.tu.darmstadt.backend.ItemShopProperties.*;

/**
 * The class {@link AccountOperations} is used for any interaction between the frontend and the backend of this
 * project.
 */
@Component
public class AccountOperations {

    public static void main(String[] args) {
        System.out.println(checkForTestUser("test"));
        System.out.println(getAccountByEmail("foo@bar.net"));
    }

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

    /**
     * This static method is used to log into an existing {@link Account} by email and password.
     *
     * @param email the email to log in with
     * @param password the password to log in with
     * @return if both username and password are correct, the {@link Account} with these data is returned.
     */
    @Contract("null, _ -> fail")
    public static @NotNull Account getAccountByUserName(@Nullable String email, String password)
        throws AccountOperationException
    {

        // TODO: make some logic that retrieves the account information, also first check whether the password is right

        if( email == null || email.isEmpty() ) {
            // If the username field is empty, this exception is thrown.
            throw new IncorrectEmailException("Username field must not be empty. Please enter a correct username.");
        }

        // The account that is accessed via email.
        Account acc = getAccountByEmail( email );

        if( acc == null ) {
            // If acc is null, it means that there is no Account with the email existing.
            throw new NoSuchAccountException("User with the email " + email + " does not exist.");
        }

        if( password == null || password.isEmpty() ) {
            // If the password field is empty, the frontend announces the user to enter a valid password.
            throw new IncorrectPasswordException("Password field must not be empty. Please enter a password.");
        }

        if( !password.equals(acc.getPassword()) ){
            // In this case, the password to the corresponding account is incorrect.
            System.out.println("Password mismatch. I give up!");
            throw new IncorrectPasswordException("Incorrect password. Please try again.");
        }

        return acc;
        // return checkForTestUser(userName);
    }

    /**
     * this is a test method until a working implementation is established
     */
    private static Account checkForTestUser(String userName) {
        if(userName.equals("test")){
            try {
                return new Account("dobreastefan68@gmail.com", "hello123", "Stefan", "Dobrea",
                        LocalDate.of(2002,12,9), "00000", AccountStatus.STANDARD, 50);
            } catch (AccountPolicyException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("method getAccountByUserName not jet implemented");
    }


    /**
     * Retrieves an {@link Account} from the data source by entering a specified email.
     * @param mail the specified email
     * @return The {@link Account} with the email. If the email does not exist, this method returns {@code null}.
     */
    public static Account getAccountByEmail(String mail) {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        Optional<Account> accountOptional = accountService.getAccountByEmail(mail);

        return accountOptional.orElse(null);
    }

    /**
     * Retrieves an {@link Account} from a data source by entering a specified ID.
     *
     * @param ID the specified ID
     * @return the {@link Account} if such an {@link Account} with the specified ID exists, otherwise {@code null}.
     * @throws AccountPolicyException is thrown if the entered ID is not in a valid format.
     */
    public static Account getAccountByID(final String ID) throws AccountPolicyException {
        if( !VALID_ID_FORMAT.test(ID) ) {
            throw new AccountPolicyException("Invalid account ID");
        }

        AccountService accountService = SpringContext.getBean(AccountService.class);
        Optional<Account> accountOptional = accountService.getAccountById(ID);

        return accountOptional.orElse(null);
    }

    public static Transaction getTransactionsByAccount(Account currentAccount) {
        return null;
    }
}
