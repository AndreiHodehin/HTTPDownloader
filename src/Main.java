import pack.Downloader;
import pack.PathHandler;
import pack.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main  {

    static  Logger logger = Logger.getLogger("Logger");
    private static int maxSpeed = 1024 ;
    private static int numOfThread = 1 ;
    private static String  fromFileName = "D:\\Itvdn_programm\\Projects\\HTTPDownloader\\from\\somePAth.txt";
    private static String  pathToFile = "D:\\Itvdn_programm\\Projects\\HTTPDownloader\\to";


        public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            try {

                System.out.println("Enter file download speed in kilobyte ");
                maxSpeed = sc.nextInt() * 1024;

                System.out.println("Enter number of threads ");
                numOfThread = sc.nextInt();

                System.out.println("The default file is a .txt document from the from folder of this project");
                System.out.println("Enter a file with resources and their names ");
                fromFileName = sc.next();

                System.out.println("The default folder is the folder To of this project");
                System.out.println("Enter target directory ");
                pathToFile = sc.next();
            } catch (Exception e){
                logger.log(Level.WARNING,"Something wrong in input");
                return;
            }

            ExecutorService executorService = Executors.newFixedThreadPool(numOfThread);

            PathHandler pathHandler = new PathHandler(fromFileName);
            ArrayList<Task> tasks = pathHandler.getTasks();
            logger.info("The source file has been processed and the tasks are ready for threading");

            for (Task task : tasks) {

                Thread thread = new Thread(() -> {

                    logger.log(Level.INFO,"pack.Task: " + task.getNameOfFile() + " are processed");

                    Downloader dw = new Downloader();
                    dw.get(task.getPath(), maxSpeed);

                    File toFile = new File(pathToFile + "\\" + task.getNameOfFile());

                    if(toFile.exists() && toFile.isDirectory()) {
                        try {

                            FileWriter fw = new FileWriter(toFile);
                            fw.write(dw.byteToChar());
                            fw.close();

                        } catch (IOException  e) {
                            logger.log(Level.WARNING,"pack.Task: " + task.getNameOfFile() + "have problem while downloading in running thread ");
                        }
                    }
                    logger.log(Level.INFO,"pack.Task: " + task.getNameOfFile() + " downloaded");
                });

                executorService.submit(thread);
            }
            executorService.shutdown();
        }
}
