package utils;

import java.io.IOException;

public class Logger {
    public static void debug(String message) {
        System.out.println(message);
    }

    public static void error(String message) {
        System.err.println(message);
    }

    public static void imageLoadError(String filename, IOException exc) {
        error("Unable to load image: " + filename + ": " + exc.getMessage());
    }

    
}

