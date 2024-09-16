package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.database.Account.AccountService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.accountOperation.*;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The {@link AccountOperations} class provides static methods for handling interactions
 * between the frontend and backend related to account operations, including account creation,
 * login, and retrieval of accounts by email or ID.
 */
@Component
public class AccountOperations {

    /**
     * Creates a new {@link Account} based on the data provided in the frontend.
     * This method handles the persistence of the account to the database.
     *
     * @param account the {@link Account} object containing the account details.
     * @return the saved {@link Account} after being persisted in the database.
     */
    public static Account createAccount(Account account) {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        return accountService.createAccount(account);
    }

    /**
     * Authenticates an {@link Account} by email and password.
     *
     * @param email the email used to log in.
     * @param password the password used to log in.
     * @return the authenticated {@link Account} if the email and password are correct.
     * @throws AccountOperationException if there is an issue with the login process.
     * @throws IncorrectEmailException if the email is null or empty.
     * @throws NoSuchAccountException if no account is found for the given email.
     * @throws IncorrectPasswordException if the password is incorrect.
     * @throws AccountClosedException if the account is closed.
     */
    @Contract("null, _ -> fail")
    public static @NotNull Account getAccountByEmail(@Nullable String email, String password)
            throws AccountOperationException {

        validateEmail(email);
        Account account = getAccountByEmail(email);

        if (account == null) {
            throw new NoSuchAccountException("No user found with email: " + email);
        }

        validatePassword(password, account);

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new AccountClosedException("The account is closed. Please contact support.");
        }

        return account;
    }

    /**
     * Retrieves an {@link Account} from the data source by the specified email.
     *
     * @param email the email associated with the account.
     * @return the {@link Account} if found, otherwise {@code null}.
     */
    public static Account getAccountByEmail(String email) {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        return accountService.getAccountByEmail(email).orElse(null);
    }

    /**
     * Retrieves an {@link Account} by the specified ID.
     *
     * @param id the ID of the account.
     * @return the {@link Account} if found, otherwise {@code null}.
     * @throws AccountPolicyException if the ID is not valid.
     */
    public static Account getAccountByID(final Long id) throws AccountPolicyException {
        // Uncomment and implement the validation if needed
        // if (!VALID_ACCOUNT_ID_FORMAT.test(id)) {
        //     throw new AccountPolicyException("Invalid account ID: " + id);
        // }

        AccountService accountService = SpringContext.getBean(AccountService.class);
        return accountService.getAccountByID(id).orElse(null);
    }

    /**
     * Retrieves all accounts from the data source.
     *
     * @return a list of all {@link Account} objects.
     */
    public static List<Account> getAllAccounts() {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        return accountService.getAllAccounts();
    }

    /**
     * Saves the provided {@link Account} to the database.
     *
     * @param account the account to save.
     */
    public static void saveAccount(Account account) {
        AccountService accountService = SpringContext.getBean(AccountService.class);
        accountService.saveAccount(account);
    }

    /**
     * Placeholder method for retrieving transactions by account.
     *
     * @param currentAccount the account to retrieve transactions for.
     * @return the {@link Transaction} associated with the account.
     */
    public static Transaction getTransactionsByAccount(Account currentAccount) {
        // TODO: Implement logic for retrieving transactions
        return null;
    }

    /**
     * Validates that the provided email is not null or empty.
     *
     * @param email the email to validate.
     * @throws IncorrectEmailException if the email is null or empty.
     */
    private static void validateEmail(String email) throws IncorrectEmailException {
        if (email == null || email.isEmpty()) {
            throw new IncorrectEmailException("Email field must not be empty. Please enter a valid email.");
        }
    }

    /**
     * Validates the provided password against the account's password.
     *
     * @param password the password to validate.
     * @param account the account to compare the password with.
     * @throws IncorrectPasswordException if the password is null, empty, or incorrect.
     */
    private static void validatePassword(String password, Account account) throws IncorrectPasswordException {
        if (password == null || password.isEmpty()) {
            throw new IncorrectPasswordException("Password field must not be empty. Please enter a password.");
        }

        if (!password.equals(account.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password. Please try again.");
        }
    }
}
