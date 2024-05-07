package de.tu.darmstadt.dataModel;

public class Item {

    private double price;

    private String name;

    private ItemImage image;

    private String description;

    public Item(double price, String name, ItemImage image, String description) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
}
