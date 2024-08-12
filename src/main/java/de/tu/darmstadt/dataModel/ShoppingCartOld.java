package de.tu.darmstadt.dataModel;

import de.tu.darmstadt.backend.backendService.AccountOperations;
import de.tu.darmstadt.backend.exceptions.accountOperation.NoSuchAccountException;
import de.tu.darmstadt.backend.exceptions.accountPolicy.AccountPolicyException;
import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.NoSuchItemException;
import de.tu.darmstadt.backend.exceptions.shoppingCart.NegativeQuantityException;
import de.tu.darmstadt.backend.exceptions.shoppingCart.ShoppingCartException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

/**
 * A {@code ShoppingCart} is a class that is used by an {@link Account} to purchase a specific amount of
 * {@link Item items}
 */
@Entity
//@Table(name = "shoppingCart")
public class ShoppingCartOld {

    // TODO: CHECK HOW TO IMPLEMENT ATTRIBUTE PAIR !!!

    @Id // TODO: CHECK IF IT IS CORRECT !!!
    @Column(name = "account_id")
    private String accountID;

    @Id // TODO: CHECK IF IT IS CORRECT !!!
    @Column(name = "item_id")
    private String itemID;

    /**
     * Stores the amount of a single {@link Item} in a {@link ShoppingCartOld} entry.
     */
    @Column(name = "amount", nullable = false)
    private int quantity;

    // :::::::::::::::::::::::::::: CONSTRUCTORS :::::::::::::::::::::::::::::

    /**
     * Constructs a new {@link ShoppingCartOld} with a specified {@link Account} ID, a specified {@link Item} and a
     * specified {@link #quantity} of the corresponding {@link Item} in the {@link ShoppingCartOld}
     *
     * @param account_id the specified {@link Account} ID
     * @param item_id the specified {@link Item} ID
     * @param quantity the specified {@link #quantity} of the corresponding {@link Item}
     *
     * @throws ShoppingCartException is thrown if any fault occurs during instantiation of {@link ShoppingCartOld}
     */
    public ShoppingCartOld(final @NotNull String account_id, final @NotNull String item_id, int quantity)
            throws ShoppingCartException, AccountPolicyException, NoSuchAccountException, NoSuchItemException,
                    InvalidItemIDFormatException
    {

        //check_if_account_exists(account_id); // Checks if an account exists: throws NoSuchAccountException
        //check_if_item_exists(item_id); // Checks if an item exists: throws ItemPropertiesException

        accountID = account_id;
        itemID = item_id;

        if(quantity < 0) {
            // Makes sure that the quantity is never negative.
            throw new NegativeQuantityException(quantity);
        } // end of if

        this.quantity = quantity;
    }

    public ShoppingCartOld() {
        // DO NOT REMOVE THIS CONSTRUCTOR
    }


    // :::::::::::::::::::::::::: AUXILIARY METHODS ::::::::::::::::::::::::::

    /**
     * Checks if an {@link Account} with the specified ID exists. If not, this method throws a
     * {@link NoSuchAccountException}. If the ID is not in a valid format, an {@link AccountPolicyException}
     * is thrown.
     *
     * @param id the specified account ID
     * @throws NoSuchAccountException is thrown if an {@link Account} with this ID does not exist
     * @throws AccountPolicyException is thrown if the specified account ID is in an invalid format
     */
    private static void check_if_account_exists(final Long id)
            throws NoSuchAccountException, AccountPolicyException
    {
        if( AccountOperations.getAccountByID(id) == null ) {
            throw new NoSuchAccountException("Account with the ID '" + id + "' does not exist");
        }
    }

    /**
     * Checks if an {@link Item} with the specified ID exists. If not, this method throws a {@link NoSuchItemException}.
     * If the ID is not in a valid format, a {@link InvalidItemIDFormatException} is thrown.
     *
     * @param item_id the specified {@link Item} ID
     *
     * @throws InvalidItemIDFormatException is thrown if the specified ID is not in a valid format
     * @throws NoSuchItemException is thrown if an {@link Item} with the specified ID does not exist
     */
    /*
    private static void check_if_item_exists(final String item_id)
            throws InvalidItemIDFormatException, NoSuchItemException
    {
        if( ItemOperations.getItemById(item_id) == null ) {
            throw new NoSuchItemException("Item with the ID '" + item_id + "' does not exist");
        }
    }

     */


    // ::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::

    /**
     * This method sets the {@link #quantity} to a new value.
     *
     * @param quantity the new value for quantity
     * @throws NegativeQuantityException is thrown if the new quantity value is negative
     */
    public void setQuantity(final int quantity) throws NegativeQuantityException {
        if(quantity < 0) {
            throw new NegativeQuantityException(quantity);
        } // end of if
        this.quantity = quantity;
    }

    /**
     * @return the {@link #quantity} of an {@link Item} in the {@link ShoppingCartOld} entry
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return the {@link Account} ID in the {@link ShoppingCartOld} entry
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * @return the {@link Item} ID in the {@link ShoppingCartOld} entry.
     */
    public String getItemID() {
        return itemID;
    }

}
