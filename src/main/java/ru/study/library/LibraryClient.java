package ru.study.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.api.DataProviderXML;
import ru.study.library.api.IDataProvider;
import ru.study.library.model.User;

import static ru.study.library.Constants.*;

public class LibraryClient {
    private static final Logger log = LoggerFactory.getLogger(LibraryClient.class);

    public static void main(String[] args) {
        try {
            IDataProvider provider = selectDataProvider(args[0]);
            switch (args[1]) {
                case USER:
                    log.info(user(provider, args));
                    break;
                default:
                    log.error("ERROR_COMMAND");
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public static IDataProvider selectDataProvider(String str) {
        switch (str) {
            case XML:
                return new DataProviderXML();
            default:
                throw new IllegalStateException("ERROR_COMMAND");
        }
    }

    public static String user(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case USER_CREATE:
                return provider.createUser(new User(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).name();
            case USER_GET:
                return provider.getUserById(Long.parseLong(s[3])).map(User::toString).orElse("User not exists");
            case USER_UPD:
                return provider.updateUser(new User(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).name();
            case USER_DEL:
                return provider.deleteUserById(Long.parseLong(s[3])).name();
            default:
                throw new IllegalStateException("ERROR_COMMAND");
        }
    }
    public static void test(String[] args) {
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
