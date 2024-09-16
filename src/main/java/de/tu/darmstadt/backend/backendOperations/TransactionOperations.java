package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.database.transaction.TransactionService;
import de.tu.darmstadt.dataModel.Transaction;

import java.util.List;

/**
 * The {@code TransactionOperations} class provides static methods for managing transactions,
 * including retrieving transactions by user ID, adding new transactions, and retrieving all transactions.
 */
public class TransactionOperations {

    /**
     * Retrieves a list of transactions for a specified user ID.
     *
     * @param id the ID of the user whose transactions are being retrieved.
     * @return a {@link List} of {@link Transaction} objects associated with the given user ID.
     */
    public static List<Transaction> getTransactionsById(Long id) {
        TransactionService transactionService = SpringContext.getBean(TransactionService.class);
        return transactionService.getTransactionsByUserID(id);
    }

    /**
     * Adds a new transaction to the database.
     *
     * @param transaction the {@link Transaction} object to be added.
     */
    public static void addTransaction(Transaction transaction) {
        TransactionService transactionService = SpringContext.getBean(TransactionService.class);
        transactionService.addTransaction(transaction);
    }

    /**
     * Retrieves all transactions from the database.
     *
     * @return a {@link List} of all {@link Transaction} objects.
     */
    public static List<Transaction> getAllTransactions() {
        TransactionService transactionService = SpringContext.getBean(TransactionService.class);
        return transactionService.getAllTransactions();
    }
}

