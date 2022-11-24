package ru.study.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryClient {
    private static final Logger log = LoggerFactory.getLogger(LibraryClient.class);

    public static void main(String[] args) {
        log.info("Just a log message.");
        log.debug("Message for debug level.");
        log.error("Failed level.");

        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }
}
