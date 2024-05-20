package de.tu.darmstadt.backend.database;

import de.tu.darmstadt.dataModel.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email);
    // You can define custom query methods here if needed
}
