package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtils {

    public static String shortenUrl(String url) throws URISyntaxException, MalformedURLException {
        URL shortenedUrl = new URI(url).toURL();
        return shortenedUrl.getProtocol() + "://" + shortenedUrl.getAuthority();
    }

}
