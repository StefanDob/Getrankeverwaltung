package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.AccountStatus;
import de.tu.darmstadt.backend.database.AccountService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public static Account getAccountByEmailAndPassword(String userName, char[] password){
        // TODO: make some logic that retrieves the account information, also first check wether the password is right
        return checkForTestUser(userName);

    }

    public static List<Transaction> getTransactionsByAccount(Account account){
        //TODO provide good implementation
        if(account.getEmail().equals("dobreastefan68@gmail.com")){
            return generateTestUserTransactions();
        }
        throw new RuntimeException("Method getTransactionsByAccount not implemented");
    }

    /**
     * this is a test method until a working implementation is established
     */
    private static List<Transaction> generateTestUserTransactions() {
            List<Transaction> transactions = new ArrayList<>();

            transactions.add(new Transaction("Stefan", "stefan", 12.0, LocalDateTime.now()));
            transactions.add(new Transaction("Stefan", "alice", 34.5, LocalDateTime.of(2023, 1, 1, 10, 0)));
            transactions.add(new Transaction("Stefan", "bob", 22.1, LocalDateTime.of(2023, 2, 2, 11, 0)));
            transactions.add(new Transaction("Stefan", "carol", 45.3, LocalDateTime.of(2023, 3, 3, 12, 0)));
            transactions.add(new Transaction("Stefan", "dave", 67.8, LocalDateTime.of(2023, 4, 4, 13, 0)));
            transactions.add(new Transaction("eve", "Stefan", 89.9, LocalDateTime.of(2023, 5, 5, 14, 0)));
            transactions.add(new Transaction("frank", "Stefan", 23.4, LocalDateTime.of(2023, 6, 6, 15, 0)));
            transactions.add(new Transaction("grace", "Stefan", 56.7, LocalDateTime.of(2023, 7, 7, 16, 0)));
            transactions.add(new Transaction("heidi", "Stefan", 78.9, LocalDateTime.of(2023, 8, 8, 17, 0)));
            transactions.add(new Transaction("ivan", "Stefan", 12.3, LocalDateTime.of(2023, 9, 9, 18, 0)));
            transactions.add(new Transaction("Stefan", "judy", 34.6, LocalDateTime.of(2023, 10, 10, 19, 0)));
            transactions.add(new Transaction("Stefan", "mallory", 55.5, LocalDateTime.of(2023, 11, 11, 20, 0)));
            transactions.add(new Transaction("Stefan", "nathan", 66.6, LocalDateTime.of(2023, 12, 12, 21, 0)));
            transactions.add(new Transaction("Stefan", "oscar", 77.7, LocalDateTime.of(2024, 1, 13, 10, 0)));
            transactions.add(new Transaction("Stefan", "peggy", 88.8, LocalDateTime.of(2024, 2, 14, 11, 0)));
            transactions.add(new Transaction("Stefan", "trent", 99.9, LocalDateTime.of(2024, 3, 15, 12, 0)));
            transactions.add(new Transaction("Stefan", "victor", 11.1, LocalDateTime.of(2024, 4, 16, 13, 0)));
            transactions.add(new Transaction("Stefan", "walter", 22.2, LocalDateTime.of(2024, 5, 17, 14, 0)));
            transactions.add(new Transaction("Stefan", "xander", 33.3, LocalDateTime.of(2024, 6, 18, 15, 0)));
            transactions.add(new Transaction("Stefan", "yvonne", 44.4, LocalDateTime.of(2024, 7, 19, 16, 0)));
            transactions.add(new Transaction("Stefan", "zara", 55.5, LocalDateTime.of(2024, 8, 20, 17, 0)));

            return transactions;

    }

    /**
     * this is a test method until a working implementation is established
     */
    private static Account checkForTestUser(String userName) {
        if(userName.equals("test")){
            try {
                return new Account("dobreastefan68@gmail.com", "hello123", "Stefan", "Dobrea",
                        LocalDate.of(2002,12,9), "017634605567", AccountStatus.STANDARD, 50);
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
