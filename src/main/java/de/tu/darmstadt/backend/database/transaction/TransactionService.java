package de.tu.darmstadt.backend.database.transaction;

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
        return transactionRepository.save(transaction);
    }
}
