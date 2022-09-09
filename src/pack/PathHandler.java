package pack;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathHandler {
    private final Logger logger = Logger.getLogger("Logger");
    private String fileName;
    private ArrayList<Task> tasks;

    public PathHandler(String fileName) {
        this.fileName = fileName;
        tasks = new ArrayList();

        File file = new File(fileName);
        if(file.exists() && file.isFile()){
            try {
                FileReader reader = new FileReader(file,Charset.forName("UTF-8"));
                char[] buffer = new char[(int) file.length()];
                reader.read(buffer);
                parse(buffer);
                reader.close();
            } catch (IOException e) {
                logger.log(Level.WARNING,"IOException while processing source file");
            }
        } else {
            logger.log(Level.WARNING,"file doesn't exist or not a file");
        }
    }

    private void parse(char[] buffer) {
        String sp = new String(buffer);
        String[] file = sp.trim().replaceAll("\r"," ").replaceAll("\n","").split(" ");

        for (int i = 0; i < file.length-1; i += 2) {
            Task task = new Task(file[i],file[i+1]);
            tasks.add(task);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

}
