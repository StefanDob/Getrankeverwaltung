package de.tu.darmstadt.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for handling operations related to items, such as downloading images.
 */
public class ItemUtils {

    /**
     * Downloads an image from the specified URL and returns it as a byte array.
     *
     * @param imageUrl the URL of the image to be downloaded
     * @return a byte array representing the image
     * @throws IOException if an I/O error occurs while opening the connection or reading the image
     */
    public static byte[] downloadImageAsByteArray(String imageUrl) throws IOException {
        // Create URL object from the provided image URL string
        URL url = new URL(imageUrl);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get the input stream from the connection
        try (InputStream inputStream = connection.getInputStream()) {
            // Read all bytes from the input stream
            return inputStream.readAllBytes();
        } finally {
            // Disconnect the connection to release resources
            connection.disconnect();
        }
    }
}
