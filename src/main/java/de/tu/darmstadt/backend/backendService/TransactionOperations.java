package de.tu.darmstadt.backend.backendService;

import de.tu.darmstadt.backend.database.ShoppingCart.ShoppingCartService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.database.transaction.TransactionService;
import de.tu.darmstadt.dataModel.Transaction;

import java.util.List;

public class TransactionOperations {
    public static List<Transaction> getTransactionsById(Long id) {
        TransactionService transactionService = SpringContext.getBean(TransactionService.class);
        return transactionService.getTransactionsByUserID(id);
    }

    public static void addTransaction(Transaction transaction){
        TransactionService transactionService = SpringContext.getBean(TransactionService.class);
        transactionService.addTransaction(transaction);
    }
}