package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByAccount(Account account);
}
