package de.tu.darmstadt.dataModel;

public class Getraenk {

    private double price;

    private String name;

    private GetraenkImage image;


    public Getraenk(double price, String name, GetraenkImage image) {
        this.price = price;
        this.name = name;
        this.image = image;
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

    public GetraenkImage getImage() {
        return image;
    }

    public void setImage(GetraenkImage image) {
        this.image = image;
    }
}
