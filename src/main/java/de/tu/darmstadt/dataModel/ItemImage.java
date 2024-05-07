package de.tu.darmstadt.dataModel;

/**
 * A
 * ItemImage is a superclass of those classes which represents a picture for an instance of {@link Item}.
 *
 * @version 30.04.2024
 * @author Toni Tan Phat Tran
 */
public class ItemImage {

    private String path;

    public ItemImage(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
