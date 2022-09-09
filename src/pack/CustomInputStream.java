package pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomInputStream extends InputStream {

    Logger logger  = Logger.getLogger("Logger");
    private static int MAX_SPEED ;
    private static final long SECOND = 1000;
    private long downloadedInOneSecond = 0L;
    private long lastTime = System.currentTimeMillis();

    private final InputStream inputStream;

    public CustomInputStream(InputStream inputStream, int MAX_SPEED) {
        this.inputStream = inputStream;
        CustomInputStream.MAX_SPEED = MAX_SPEED;
    }
    @Override
    public int read() throws IOException {

        long currentTime;

        if((downloadedInOneSecond >= MAX_SPEED)
                && (((currentTime = System.currentTimeMillis())-lastTime) < SECOND)){
            try {
                logger.log(Level.WARNING,"Thread stopped because download speed limit exceeded");
                Thread.sleep(SECOND-(currentTime-lastTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            downloadedInOneSecond = 0;
            lastTime = System.currentTimeMillis();
        }

        int res = inputStream.read();
        if(res >= 0) {
            downloadedInOneSecond++;
        }
        return res;
    }
}
