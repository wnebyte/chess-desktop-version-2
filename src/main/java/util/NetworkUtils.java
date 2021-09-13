package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public final class NetworkUtils {

    private static final List<String> ADDRESSES = Arrays.asList
            (
                    "http://checkip.amazonaws.com/",
                    "http://myexternalip.com/raw",
                    "http://ipecho.net/plain",
                    "http://bot.whatismyipaddress.com",
                    "https://ipv4.icanhazip.com/"
            );

    public static boolean isConnected() {
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            connection.getInputStream().close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getExternalAddress() {
        for (String address : ADDRESSES) {
            try {
                URL url = new URL(address);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                return in.readLine();
            }
            catch (IOException ignored) { }
        }
        return null;
    }
}
