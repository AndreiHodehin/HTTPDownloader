package pack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Downloader {
    Logger logger  = Logger.getLogger("Logger");
    private byte[] buffer;
    public byte[] get(String urlName, int maxSpeed) {

        try {

            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            InputStream is = conn.getInputStream();
            CustomInputStream customIS = new CustomInputStream(is, maxSpeed);

            buffer = new byte[is.available()];

            customIS.read(buffer);

            is.close();
            customIS.close();

        } catch (IOException e) {
            logger.log(Level.WARNING,"Exception while downloading");
        }
        return buffer;
    }

    public byte [] getBuffer() {
        return buffer;
    }

    public char[] byteToChar(){
        char[] chars = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            chars[i] = (char) buffer[i];
        }
        return chars;
    }
}
