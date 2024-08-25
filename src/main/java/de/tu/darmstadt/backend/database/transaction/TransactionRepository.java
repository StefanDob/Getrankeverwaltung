package de.tu.darmstadt.backend.database.transaction;

import de.tu.darmstadt.dataModel.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderOrReceiver(Long senderId, Long receiverId);
}
