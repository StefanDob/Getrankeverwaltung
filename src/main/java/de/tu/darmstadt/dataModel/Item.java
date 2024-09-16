package de.tu.darmstadt.dataModel;

import com.vaadin.flow.server.StreamResource;
import de.tu.darmstadt.backend.backendOperations.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayInputStream;

import java.util.Arrays;


/**
 * Represents an item in the drink shop. Each {@link Item} is identified by a unique {@code id}, and has attributes such
 * as a {@code name}, {@code price}, {@code image}, {@code description}, and {@code stock}.
 */
@Entity
@Table(name = "item")
public class Item {

    /**
     * A unique identifier for the {@link Item}. It serves as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the {@link Item}. It is a unique and non-nullable attribute.
     */
    @Column(name = "name", unique = true, nullable = false)
    private @NotNull String name;

    /**
     * The price of the {@link Item}, stored as a decimal number.
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * The image of the {@link Item} stored as a binary large object (BLOB).
     */
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    /**
     * The description of the {@link Item}.
     */
    @Column(name = "description")
    private String description;

    /**
     * The available stock for the {@link Item}.
     */
    @Column(name = "stock")
    private int stock;


    // :::::::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::

    /**
     * Constructs a new {@link Item} with a specified {@code name}, {@code price}, {@code stock}, {@code image},
     * and {@code description}.
     *
     * @param name the specified name of the item
     * @param price the specified price of the item
     * @param stock the quantity available in stock
     * @param image the image associated with the item
     * @param description the description of the item
     *
     * @throws ItemPropertiesException if the item data does not meet the requirements.
     */
    public Item(@NotNull String name, double price, int stock, byte[] image, String description) {
        this.name = name.trim();
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.description = description;
    }

    /**
     * Default constructor used for JPA.
     */
    public Item() {}

    // :::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    /**
     * Gets the price of the {@link Item}.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the {@link Item}.
     *
     * @param price the new price of the item
     * @throws NegativePriceException if the price is negative.
     */
    public void setPrice(double price) throws NegativePriceException {
        if(price < 0) {
            throw new NegativePriceException(price);
        }
        this.price = price;
    }

    /**
     * Gets the unique identifier of the {@link Item}.
     *
     * @return the id of the item
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the {@link Item}.
     *
     * @return the name of the item
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Item}.
     *
     * @param name the new name of the item
     */
    public void setName(@NotNull String name) {
        this.name = name.trim();
    }

    /**
     * Gets the image of the {@link Item}.
     *
     * @return the image of the item as a byte array
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image of the {@link Item}.
     *
     * @param image the new image of the item as a byte array
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Gets the description of the {@link Item}.
     *
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the {@link Item}.
     *
     * @param description the new description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the stock quantity of the {@link Item}.
     *
     * @return the stock quantity
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the {@link Item}.
     *
     * @param stock the new stock quantity
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * Returns a string representation of the {@link Item}.
     * The format is: {@code [name: price]}.
     *
     * @return the string representation of the item
     */
    @Override
    public String toString() {
        return "[" + getName() + ": " + getPrice() + "]";
    }

    /**
     * Compares this {@link Item} with another object for equality.
     *
     * @param o the object to compare with
     * @return true if the items are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if( o instanceof Item item ) {
            return item.getPrice() == getPrice()
                    && item.getName().equals(getName())
                    && item.getDescription().equals(getDescription())
                    && Arrays.equals(item.getImage(), getImage());
        }
        return false;
    }

    /**
     * Returns the image of the {@link Item} as a {@link StreamResource}.
     *
     * @return the image of the item as a stream resource
     */
    public StreamResource getImageAsResource() {
        return new StreamResource("image.jpg", () -> new ByteArrayInputStream(image));
    }

    /**
     * Gets the price of the {@link Item} formatted as a string in the format: "price €".
     *
     * @return the formatted price string
     */
    public String getItemPriceAsString() {
        return String.format("%.2f €", price);
    }
}

