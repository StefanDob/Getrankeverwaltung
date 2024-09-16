package de.tu.darmstadt.dataModel.shoppingCart;

import de.tu.darmstadt.dataModel.Item;
import jakarta.persistence.*;


/**
 * The {@link ShoppingCartItem} entity represents an item in a {@link ShoppingCart}.
 * Each shopping cart item is associated with a specific {@link Item} and maintains the quantity of that item
 * in the shopping cart. This class serves as a bridge between a shopping cart and an item in the system.
 */
@Entity
public class ShoppingCartItem {

    /**
     * The unique identifier for the shopping cart item. It is auto-generated and serves as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link ShoppingCart} to which this {@link ShoppingCartItem} belongs.
     * Each shopping cart item is part of one shopping cart, forming a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    /**
     * The {@link Item} associated with this {@link ShoppingCartItem}.
     * This represents the product added to the shopping cart, forming a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    /**
     * The quantity of the {@link Item} in this {@link ShoppingCartItem}.
     * It stores how many units of the item are present in the shopping cart.
     */
    private int quantity;

    // ::::::::::::::::::::::::::::::::: GETTERS AND SETTERS :::::::::::::::::::::::::::::::::

    /**
     * Retrieves the unique ID of this {@link ShoppingCartItem}.
     *
     * @return the shopping cart item ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of this {@link ShoppingCartItem}.
     *
     * @param id the shopping cart item ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the {@link ShoppingCart} to which this {@link ShoppingCartItem} belongs.
     *
     * @return the shopping cart that contains this item
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the {@link ShoppingCart} to which this {@link ShoppingCartItem} belongs.
     *
     * @param shoppingCart the shopping cart to associate with this item
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Retrieves the {@link Item} associated with this {@link ShoppingCartItem}.
     *
     * @return the item in the shopping cart
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the {@link Item} associated with this {@link ShoppingCartItem}.
     *
     * @param item the item to associate with this shopping cart item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Retrieves the quantity of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return the quantity of the item in the shopping cart
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @param quantity the quantity of the item to set in the shopping cart
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ::::::::::::::::::::::::::::::: ADDITIONAL METHODS ::::::::::::::::::::::::::::::::

    /**
     * Retrieves the name of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return the name of the item
     */
    public String getName() {
        return item.getName();
    }

    /**
     * Retrieves the description of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return the description of the item
     */
    public String getDescription() {
        return getItem().getDescription();
    }

    /**
     * Retrieves the price of a single unit of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return a string representing the price of the item with a currency symbol
     */
    public String getPriceAsString() {
        return getItem().getItemPriceAsString();
    }

    /**
     * Retrieves the price of a single unit of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return getItem().getPrice();
    }

    /**
     * Retrieves the available stock of the {@link Item} in this {@link ShoppingCartItem}.
     *
     * @return the available stock of the item
     */
    public double getStock() {
        return item.getStock();
    }

    /**
     * Retrieves the available stock of the {@link Item} as a string.
     *
     * @return the available stock of the item as a string
     */
    public String getStockAsString() {
        return String.valueOf(item.getStock());
    }
}


