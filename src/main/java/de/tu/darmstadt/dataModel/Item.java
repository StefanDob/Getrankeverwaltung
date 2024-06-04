package de.tu.darmstadt.dataModel;

import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import static de.tu.darmstadt.ProjectUtils.*;
import static de.tu.darmstadt.dataModel.ExceptionChecker.*;

/**
 * An {@link Item} is a class of instances sold in the drink shop.
 */
@Entity
@Table(name = "item")
public class Item {


    public static void main(String[] args) throws InvalidItemIDFormatException {
        for(int i = 0 ; i < 50 ; i++)
            System.out.println(generate_item_ID());
    }

    /**
     * An ITEM_ID is a unique attribute that clearly identifies the corresponding {@link Item}. It is as a primary
     * key for the {@link Item}.
     */

    @Id
    @Column(name = "item_id", unique = true, nullable = false)
    private final String ITEM_ID;

    /**
     * A name is a unique attribute used to clearly identify the corresponding {@link Item}.
     */
    @Column(name = "item_name", unique = true, nullable = false)
    private @NotNull String name;

    /**
     * Stores the price of an {@link Item} as a decimal number.
     */
    @Column(name = "item_price", nullable = false)
    private double price;

    /**
     * The {@link ItemImage image} is an attribute to display an image of the {@link Item}.
     */
    @Embedded // @Embedded is important to make JPA running. So don't remove it, please.
    private ItemImage image;

    /**
     * This attribute stores the description of the {@link Item}.
     */
    @Column(name = "item_description")
    private String description;

    // ::::::::::::::::::::::::::::::: PROPERTIES ::::::::::::::::::::::::::::::::

    /**
     * The prefix of an item ID. It should never be altered again.
     */
    private static final String ID_PREFIX = "IT-";

    /**
     * This {@link Predicate} checks if the ID of {@link Item} is in the following format:
     * <p>
     *     {@code "IT-XXXXXX"}
     *
     * <p>
     *     The ID always starts with "IT", followed by a '-' that separates the "IT" and "XXXXXX".
     *     Each 'X' is a placeholder for any digit.
     */
    public final static Predicate<? super String> ITEM_ID_FORMAT = s -> {
        if( !s.subSequence(0, 3).equals(ID_PREFIX) || s.length() != 9 ) {
            return false;
        }

        for (int i = 3 ; i < s.length() ; i++) {
            if( !Character.isDigit(s.charAt(i)) ) {
                return false;
            }
        } // end of for

        return true;
    };


    // :::::::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::

    /**
     * Constructs a new {@link Item} with a specified {@link #price}, {@link #name}, {@link #image} and
     * {@link #description}.
     *
     * @param price the specified price
     * @param name the specified name
     * @param image the specified image
     * @param description the specified description
     *
     * @throws ItemPropertiesException is thrown if the specified product data do not meet the specified requirements.
     */
    public Item(double price, @NotNull String name, ItemImage image, String description)
        throws ItemPropertiesException
    {
        this();

        if(price < 0) {
            throw new NegativePriceException(price);
        } // end of if

        this.price = price;
        this.name = name;
        this.image = image;
        this.description = description;

    }

    /**
     * A default constructor is used to add an {@link Item} to the data source.
     */
    public Item() throws InvalidItemIDFormatException {
        // DO NOT REMOVE THIS CONSTRUCTOR AND DO NOT CHANGE ANYTHING !!!
        ITEM_ID = generate_item_ID();
    }

    // ::::::::::::::::::::::::::::::: AUXILIARY METHODS ::::::::::::::::::::::::::::::::::

    /**
     * This static method generates an ID for an {@link Item}.
     * TODO: Implement how to check if the ID is already contained.
     *
     * @return the randomly generated ID for an {@link Item}
     */
    private static String generate_item_ID() throws InvalidItemIDFormatException {
        String result;

        do {
            result = ID_PREFIX + ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
        } while (is_item_ID_already_existing(result));

        if( !ITEM_ID_FORMAT.test(result) ) {
            throw new InvalidItemIDFormatException(result);
        }

        return result;
    }

    private static boolean is_item_ID_already_existing(String ID) {
        return false;
    }


    // :::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws NegativePriceException {
        if(price < 0) {
            throw new NegativePriceException(price);
        } // end of if

        this.price = price;
    }

    public String get_ITEM_ID() {
        return ITEM_ID;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public ItemImage getImage() {
        return image;
    }

    public void setImage(ItemImage image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {

        if(this == o) return true;

        if( o instanceof Item item ) {
            return item.getPrice() == getPrice()
                    && item.getName().equals(getName())
                    && item.getDescription().equals(getDescription())
                    && item.getImage().equals(getImage());
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + getName() + ": " + getPrice() + "]";
    }
}
