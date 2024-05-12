package de.tu.darmstadt.dataModel;


/**
 * An {@link ItemImage} is a class used to display an image of an {@link Item} by retrieving image resources.
 */
public class ItemImage {

    private String path;

    public ItemImage(String path) {
        this.path = path;
    }

    // :::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if( o instanceof ItemImage itemImage ) {
            return path.equals(itemImage.getPath());
        }

        return false;
    }

    @Override
    public String toString() {
        return "ItemImage [path=" + path + "]";
    }
}
