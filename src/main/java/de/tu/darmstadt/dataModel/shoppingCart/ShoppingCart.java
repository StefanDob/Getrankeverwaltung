package de.tu.darmstadt.dataModel.shoppingCart;

import de.tu.darmstadt.dataModel.Account;
import de.tu.darmstadt.dataModel.Item;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ShoppingCart} entity represents a shopping cart associated with an {@link Account}.
 * Each shopping cart can contain multiple {@link ShoppingCartItem} objects.
 * The cart is tied to an account, and its items are managed via a one-to-many relationship.
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    /**
     * The unique identifier for the shopping cart. It is auto-generated and serves as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link Account} associated with this {@link ShoppingCart}.
     * Each shopping cart belongs to one account, forming a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * A list of {@link ShoppingCartItem} objects associated with this {@link ShoppingCart}.
     * The items are mapped by the "shoppingCart" field in the {@link ShoppingCartItem} class.
     * This list is eagerly fetched and all items are cascaded and removed when the shopping cart is removed.
     */
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ShoppingCartItem> items = new ArrayList<>();

    // ::::::::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::::::

    /**
     * Default constructor for JPA. It initializes an empty shopping cart.
     */
    public ShoppingCart() {
    }

    // :::::::::::::::::::::::::::::::: GETTERS AND SETTERS :::::::::::::::::::::::::::::

    /**
     * Retrieves the unique ID of this {@link ShoppingCart}.
     *
     * @return the shopping cart ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of this {@link ShoppingCart}.
     *
     * @param id the shopping cart ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the list of {@link ShoppingCartItem} objects in this {@link ShoppingCart}.
     *
     * @return the list of items in the shopping cart
     */
    public List<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Sets the list of {@link ShoppingCartItem} objects in this {@link ShoppingCart}.
     *
     * @param items the list of shopping cart items to set
     */
    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    /**
     * Retrieves the {@link Account} associated with this {@link ShoppingCart}.
     *
     * @return the account tied to the shopping cart
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the {@link Account} associated with this {@link ShoppingCart}.
     *
     * @param account the account to associate with this shopping cart
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}

