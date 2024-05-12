package de.tu.darmstadt.dataModel;

import org.jetbrains.annotations.NotNull;

/**
 * An {@link Item} is a class of instances sold in the drink shop.
 */
public class Item {

    private double price;

    private @NotNull String name;

    private ItemImage image;

    private String description;

    public Item(double price, @NotNull String name, ItemImage image, String description) {
        this.price = price;
        this.name = name;
        this.image = image;
        this.description = description;
    }


    // :::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
