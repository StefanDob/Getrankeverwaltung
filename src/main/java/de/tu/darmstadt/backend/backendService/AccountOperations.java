package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.database.Account.AccountService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public static @NotNull Account getAccountByEmail(@Nullable String email, String password)
        throws AccountOperationException
    {

        // TODO: make some logic that retrieves the account information, also first check whether the password is right
        if(email.equals("test")){
            return checkForTestUser(email);
        }
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
    }

    /**
     * this is a test method until a working implementation is established
     */
    private static Account checkForTestUser(String userName) {
        if(userName.equals("test")){
            try {
                return new Account("dobreastefan68@gmail.com", "hello123", "Stefan", "Dobrea",
                        new Date(122, 7, 12), "00000", AccountStatus.STANDARD, 50);
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
    public static Account getAccountByID(final Long ID) throws AccountPolicyException {

        //TODO redo or delete
        //if( !VALID_ACCOUNT_ID_FORMAT.test(ID) ) {
        //    throw new AccountPolicyException("Invalid account ID: " + ID);
        //}

        AccountService accountService = SpringContext.getBean(AccountService.class);
        Optional<Account> accountOptional = accountService.getAccountByID(ID);

        return accountOptional.orElse(null);
    }

    public static Transaction getTransactionsByAccount(Account currentAccount) {
        return null;
    }

    public static List<Account> getAllAccounts() {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        return accountService.getAllAccounts();
    }

    private static ArrayList<Account> getExampleAccountsOld() {
        //these accounts were generated by chatGpt
        ArrayList<Account> accounts = new ArrayList<>();
        /*
        try {

            accounts.add(new Account("johndoe@example.com", "password123", "John", "Doe", LocalDate.of(1990, 1, 1), "1234567890"));
            accounts.add(new Account("janesmith@example.com", "password456", "Jane", "Smith", LocalDate.of(1985, 2, 2), "0987654321"));
            accounts.add(new Account("alicejohnson@example.com", "password789", "Alice", "Johnson", LocalDate.of(1992, 3, 3), "5555555555"));
            accounts.add(new Account("bobbrown@example.com", "password321", "Bob", "Brown", LocalDate.of(1988, 4, 4), "4444444444"));
            accounts.add(new Account("charliedavis@example.com", "password654", "Charlie", "Davis", LocalDate.of(1995, 5, 5), "3333333333"));
            accounts.add(new Account("daveevans@example.com", "password987", "Dave", "Evans", LocalDate.of(1980, 6, 6), "2222222222"));
            accounts.add(new Account("evefranklin@example.com", "password000", "Eve", "Franklin", LocalDate.of(1993, 7, 7), "1111111111"));
            accounts.add(new Account("frankgrant@example.com", "password111", "Frank", "Grant", LocalDate.of(1979, 8, 8), "9999999999"));
            accounts.add(new Account("gracehall@example.com", "password222", "Grace", "Hall", LocalDate.of(1991, 9, 9), "88888888"));
            accounts.add(new Account("hankiglesias@example.com", "password333", "Hank", "Iglesias", LocalDate.of(1986, 10, 10), "7777777777"));
            accounts.add(new Account("ivyjackson@example.com", "password444", "Ivy", "Jackson", LocalDate.of(1994, 11, 11), "6666666666"));
            accounts.add(new Account("jackkelly@example.com", "password555", "Jack", "Kelly", LocalDate.of(1982, 12, 12), "5556667777"));
            accounts.add(new Account("katelewis@example.com", "password666", "Kate", "Lewis", LocalDate.of(1987, 1, 13), "4445556666"));
            accounts.add(new Account("leomartin@example.com", "password777", "Leo", "Martin", LocalDate.of(1990, 2, 14), "3334445555"));
            accounts.add(new Account("mianelson@example.com", "password888", "Mia", "Nelson", LocalDate.of(1983, 3, 15), "2223334444"));
            accounts.add(new Account("nickowens@example.com", "password999", "Nick", "Owens", LocalDate.of(1996, 4, 16), "1112223333"));
            accounts.add(new Account("oliviaperez@example.com", "password000", "Olivia", "Perez", LocalDate.of(1992, 5, 17), "0001112222"));
            accounts.add(new Account("peterquinn@example.com", "password1234", "Peter", "Quinn", LocalDate.of(1989, 6, 18), "9998887777"));
            accounts.add(new Account("quinnryan@example.com", "password5678", "Quinn", "Ryan", LocalDate.of(1991, 7, 19), "8887776666"));
            accounts.add(new Account("rachelsmith@example.com", "password91011", "Rachel", "Smith", LocalDate.of(1990, 8, 20), "7776665555"));

        }catch (AccountPolicyException ex){

            System.out.println(ex.getMessage());
        }

             */
        return accounts;
    }
}
