package edu.bu.met.cs665.Library;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsManager {

    private static final String LOG_FILE_PATH = System.getProperty("user.dir") + "/logs.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void log(String message) {
        String timestamp = DATE_FORMAT.format(new Date());
        String logMessage = timestamp + " - " + message;

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
