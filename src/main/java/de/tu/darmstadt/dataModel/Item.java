package de.tu.darmstadt.dataModel;

import com.vaadin.flow.server.StreamResource;
import de.tu.darmstadt.backend.backendService.ItemOperations;
import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.backend.exceptions.items.NegativePriceException;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayInputStream;

import java.util.Arrays;

/**
 * An {@link Item} is a class of instances sold in the shop.
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
     * The image is an attribute to display an image of the {@link Item}.
     */
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    /**
     * This attribute stores the description of the {@link Item}.
     */
    @Column(name = "description")
    private String description;

    @Column(name = "stock")
    private int stock;




    // :::::::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::

    /**
     * Constructs a new {@link Item} with a specified {@link #price}, {@link #name}, {@link #image} and
     * {@link #description}.+
     * TODO @Toni if you have time delete this constructor as it is not the way jpa wants you to create objects, it is better
     * to create objects with an empty constructor and then set the attributes via methods
     *
     * @param price the specified price
     * @param name the specified name
     * @param image the specified image
     * @param description the specified description
     *
     * @throws ItemPropertiesException is thrown if the specified product data do not meet the specified requirements.
     */
    public Item( @NotNull String name, double price, int stock, byte[] image, String description) {
        this.price = price;
        this.stock = stock;
        this.name = name.trim();
        this.image = image;
        this.description = description;
    }

    public Item() {}


    // ::::::::::::::::::::::::::::::: AUXILIARY METHODS ::::::::::::::::::::::::::::::::::



    /**
     * Checks if a specified ID already exists.
     * @param ID the specified ID
     * @return true if the ID already exists.
     * //TODO @Toni  move this method to the ItemOperations class
     */
    private static boolean isItemIDAlreadyExisting(Long ID) throws InvalidItemIDFormatException {
        return ItemOperations.getItemById(ID) != null;
    }

    /**
     * Checks if a specified item name already exists.
     * @param item_name the specified item name
     * @return true if the item name already exists.
     * //TODO @Toni  move this method to the ItemOperations class and implement it
     */
    private static boolean isItemNameAlreadyInUse(String item_name) {
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

    public Long getId() {
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

                    && Arrays.equals(item.getImage(), getImage()); // If any error occurs,
            // replace this line with "item.getImage().equals(getImage())"
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

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getItemPriceAsString() {
        return String.format("%.2f €", price);
    }
}
