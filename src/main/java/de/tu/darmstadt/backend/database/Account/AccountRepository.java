package de.tu.darmstadt.backend.database.Account;

import de.tu.darmstadt.dataModel.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * This method returns an {@link Optional<Account>} by a specified email.
     * @param email the specified email
     * @return the {@link Optional<Account>}
     */
    Optional<Account> findByEmail(String email);
    // You can define custom query methods here if needed

}
