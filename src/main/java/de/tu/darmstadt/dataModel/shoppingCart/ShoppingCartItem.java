package de.tu.darmstadt.dataModel.shoppingCart;

import de.tu.darmstadt.dataModel.Item;
import jakarta.persistence.*;

@Entity
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;


    public Long getId() {
        return id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return item.getName();
    }

    public String getDescription() {
        return getItem().getDescription();
    }

    /**
     *
     * @return price for the item not for the item sum
     */
    public double getPrice() {
        return getItem().getPrice();
    }

    public double getStock() {
        return item.getStock();
    }
}

