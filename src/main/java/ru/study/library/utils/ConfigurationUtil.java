package ru.study.library.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

import static ru.study.library.Constants.*;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 */
public class ConfigurationUtil {
    private final static Logger log =  LoggerFactory.getLogger(ConfigurationUtil.class);

    /**
     * Hides default constructor
     */
    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException{
        File configFile;
        configFile = CONFIG_PATH == null ? new File(DEFAULT_CONFIG_PATH) : new File(CONFIG_PATH);
        try (InputStream instr = new FileInputStream(configFile)) {
            log.info("Configuration path: " + configFile);
            configuration.load(instr);
        } catch (IOException e) {
            log.error(String.valueOf(e));
            throw new IOException(e);
        }
    }

    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }

}

