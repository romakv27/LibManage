package ru.study.library;

import java.util.Properties;

public class Constants {

    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    public static final String CONFIG_PATH = System.getProperty("config");
    public static final Properties configuration = new Properties();


    public static final String XML_USER = "xmlUser";

}
