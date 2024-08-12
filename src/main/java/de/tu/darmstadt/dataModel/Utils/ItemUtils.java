package de.tu.darmstadt.dataModel.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ItemUtils {
    public static byte[] downloadImageAsByteArray(String imageUrl) throws IOException {

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        byte[] imageUrlBytes = inputStream.readAllBytes();
        inputStream.close();

        return imageUrlBytes;

    }
}
