package de.tu.darmstadt.dataModel;

import com.vaadin.flow.server.StreamResource;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayInputStream;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/**
 * An {@link Item} is a class of instances sold in the drink shop.
 */
@Entity
@Table(name = "item")
public class Item {

    /**
     * An ITEM_ID is a unique attribute that clearly identifies the corresponding {@link Item}. It is as a primary
     * key for the {@link Item}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * A name is a unique attribute used to clearly identify the corresponding {@link Item}.
     */
    @Column(name = "name", unique = true, nullable = false)
    private @NotNull String name;

    /**
     * Stores the price of an {@link Item} as a decimal number.
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * The {@link ItemImage image} is an attribute to display an image of the {@link Item}.
     */
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    /**
     * This attribute stores the description of the {@link Item}.
     */
    @Column(name = "description")
    private String description;

    // ::::::::::::::::::::::::::::::: PROPERTIES ::::::::::::::::::::::::::::::::

    /**
     * The prefix of an item ID. It should never be altered again.
     */
    private static final String ID_PREFIX = "IT-";

    /**
     * This constant determines the length of the X...X part in {@link Item#id}.
     */
    private static final int ID_OFFSET_LENGTH = 6;

    /**
     * This {@link Predicate} checks if the ID of {@link Item} is in the following format:
     * <p>
     *     {@code "IT-X...X"}
     *
     * <p>
     *     The ID always starts with "IT", followed by a '-' that separates the "IT" and "X...X".
     *     Each 'X' is a placeholder for any digit.
     */
    public final static Predicate<? super String> ITEM_ID_FORMAT = s -> {
        if( !s.subSequence(0, ID_PREFIX.length()).equals(ID_PREFIX)
                || s.length() != ID_PREFIX.length() + ID_OFFSET_LENGTH )
        {
            return false;
        }

        for (int i = 3 ; i < s.length() ; i++) {
            if( !Character.isDigit(s.charAt(i)) ) {
                return false;
            } // end of if
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
    public Item( @NotNull String name, double price, byte[] image, String description) {
        this.price = price;
        this.name = name.trim();
        this.image = image;
        this.description = description;
    }

    public Item() {}


    // ::::::::::::::::::::::::::::::: AUXILIARY METHODS ::::::::::::::::::::::::::::::::::

    /**
     * This static method generates an ID for an {@link Item}.
     * TODO: Implement how to check if the ID is already contained.
     *
     * @return the randomly generated ID for an {@link Item}
     */
    /*

    private static String generate_item_ID() throws InvalidItemIDFormatException {
        String result;

        do {
            result = ID_PREFIX + build_ID_offset(0, 10);
        } while (is_item_ID_already_existing(result));

        if( !ITEM_ID_FORMAT.test(result) ) {
            throw new InvalidItemIDFormatException(result);
        }

        return result;
    }

     */

    /**
     * This method is used to construct the "X...X" part of the {@link Item#id}.
     *
     * @param min_number the minimum occurring digit (inclusive)
     * @param max_number the maximum occurring digit (exclusive)
     * @return the offset
     */
    /*
    private static @NotNull String build_ID_offset(int min_number, int max_number) {
        if(min_number < 0 || max_number < 1 || max_number < min_number) {
            throw new IllegalArgumentException
                    ("Invalid arguments. Expected: min_number, max_number > 1 and min_number < max_number");
        }

        StringBuilder sb = new StringBuilder(ID_OFFSET_LENGTH);

        for(int i = 0 ; i < ID_OFFSET_LENGTH ; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(min_number, max_number));
        } // end of for

        return sb.toString();
    }

     */

    /**
     * Checks if a specified ID already exists.
     * @param ID the specified ID
     * @return true if the ID already exists.
     */
    private static boolean is_item_ID_already_existing(Long ID) throws InvalidItemIDFormatException {
        return ItemOperations.getItemById(ID) != null;
    }

    /**
     * Checks if a specified item name already exists.
     * @param item_name the specified item name
     * @return true if the item name already exists.
     */
    private static boolean is_item_name_already_in_use(String item_name) {
        item_name = item_name.trim();

        return false; // TODO: IMPLEMENT
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

    public Long get_ITEM_id() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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


    public StreamResource getImageAsResource() {
        return new StreamResource("image.jpg", () -> new ByteArrayInputStream(image));
    }
}
