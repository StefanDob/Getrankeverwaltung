package de.tu.darmstadt.backend.database.transaction;

import de.tu.darmstadt.backend.backendOperations.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsByUserID(Long userId) {
        return transactionRepository.findBySenderOrReceiver(userId, userId);
    }

    @Transactional
    public Transaction addTransaction(Transaction transaction) {
        try {
            Account receiver = AccountOperations.getAccountByID(transaction.getReceiver());
            receiver.setSaldo(receiver.getSaldo() + transaction.getAmount());
            Account sender = AccountOperations.getAccountByID(transaction.getSender());
            sender.setSaldo(sender.getSaldo() - transaction.getAmount());
        } catch (AccountPolicyException e) {
            //TODO make this better
            throw new RuntimeException(e);
        }
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
