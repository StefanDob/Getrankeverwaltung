package de.tu.darmstadt.backend.database.ShoppingCart;

import de.tu.darmstadt.dataModel.shoppingCart.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
}
