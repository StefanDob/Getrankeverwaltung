package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.ShoppingCart;
import de.tu.darmstadt.dataModel.ShoppingCartOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByAccount(Account account);
}
