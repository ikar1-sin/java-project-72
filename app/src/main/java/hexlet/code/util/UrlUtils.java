package hexlet.code.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtils {

    public static String shortenUrl(String url) throws URISyntaxException, MalformedURLException {
        try {
            URL shortenedUrl = new URI(url).toURL();
            return shortenedUrl.getProtocol() + "://" + shortenedUrl.getAuthority();
        } catch (MalformedURLException e) {
            return "Некорректный URL";
        }

    }

    public static int getStatus(String siteUrl) throws URISyntaxException {
        try {
            URL url = new URI(siteUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getResponseCode();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

}
