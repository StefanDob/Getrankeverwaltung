package de.tu.darmstadt.backend;

/**
 * {@link IceCream} refers to an edible item made of frozen milk and is a popular dish
 * in warm weather.
 */
public class IceCream {

    /**
     * The name of the {@link IceCream} flavor.
     */
    private String flavor;

    /**
     * The price of an {@link IceCream} item.
     */
    private double price;


    // :::::::::::::::::::::::::::::::::: CONSTRUCTORS ::::::::::::::::::::::::::::::::::::::

    /**
     * Constructs a new instance of {@link IceCream} with a specified {@link #flavor} and a specified {@link #price}.
     * @param flavor the specified flavor
     * @param price the specified price
     */
    public IceCream(String flavor, double price) {
        this.flavor = flavor; // the flavor
        this.price = price; // the price
    }


    // ::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::::::

    /**
     * Returns the {@link #flavor} of the {@link IceCream}.
     * @return the {@link #flavor} of the {@link IceCream}
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Sets the flavor of the {@link IceCream} to a new value.
     * @param flavor the new flavor
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Returns the {@link #price} of the {@link IceCream}.
     * @return the {@link #price} of the {@link IceCream}
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the {@link IceCream} to a new value.
     * @param price the new flavor
     */
    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj instanceof IceCream iceCream) {
            return flavor.equals(iceCream.flavor) && price == iceCream.price;
        }

        return false;
    }

    /**
     * Returns the {@link String} representation of {@link IceCream}, e.g.:
     * <p>
     * {@code IceCream ic = new IceCream('White Chocolate', 1.50);}
     * </p>
     * <p>
     * The {@link String} representation is: {@code White Chocolate: 1.50}.
     * </p>
     *
     * @return the {@link String} representation of {@link IceCream}
     */
    @Override
    public String toString() {
        return flavor + ": " + price;
    }

}
