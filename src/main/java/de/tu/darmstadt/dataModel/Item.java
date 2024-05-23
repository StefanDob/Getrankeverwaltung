package de.tu.darmstadt.dataModel;

import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import static de.tu.darmstadt.ProjectUtils.*;
import static de.tu.darmstadt.dataModel.ExceptionChecker.*;

/**
 * An {@link Item} is a class of instances sold in the drink shop.
 */
public class Item {

    private double price;

    private @NotNull String name;

    private ItemImage image;

    private String description;

    private static final String ID_PREFIX = "IT-";

    /**
     * This {@link Predicate} checks if the ID of {@link Item} is in the following format:
     * <p>
     *     {@code "IT-XXXXX"}
     *
     * <p>
     *     The ID always starts with "IT", followed by a '-' that separates the "IT" and "XXXXXX".
     *     Each 'X' is a placeholder for any digit.
     */
    public final static Predicate<? super String> ITEM_ID_FORMAT = s -> {
        if( !s.subSequence(0, 3).equals(ID_PREFIX) || s.length() != 8 ) {
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
        if(price < 0) {
            throw new NegativePriceException(price);
        } // end of if

        this.price = price;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    // ::::::::::::::::::::::::::::::: AUXILIARY METHODS ::::::::::::::::::::::::::::::::::

    /**
     * This static method generates an ID for an {@link Item}.
     * TODO: Implement how to check if the ID is already contained.
     *
     * @return the randomly generated ID for an {@link Item}
     * @throws InvalidItemIDFormatException is thrown if the ID is not in a valid {@link Item#ITEM_ID_FORMAT}.
     */
    private static String generate_item_ID() throws InvalidItemIDFormatException {
        String result = ID_PREFIX; // + String.valueOf();

        return result;
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
