package de.tu.darmstadt.dataModel;

/**
 * A GetraenkImage is a superclass of those classes which represents a picture for an instance of {@link Getraenk}.
 *
 * @version 30.04.2024
 * @author Toni Tan Phat Tran
 */
public class GetraenkImage {

    private String path;


    public GetraenkImage(String path) {
        this.path = path;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
