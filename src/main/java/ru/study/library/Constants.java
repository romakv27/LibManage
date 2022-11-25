package ru.study.library;

import java.util.Properties;

public class Constants {

    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    public static final String CONFIG_PATH = System.getProperty("config");
    public static final Properties configuration = new Properties();

    //CLI
    public static final String XML = "xml";
    public static final String USER = "user";

    public static final String USER_CREATE = "create";
    public static final String USER_GET = "get";
    public static final String USER_UPD = "upd";
    public static final String USER_DEL = "del";

    public static final String XML_USER = "xmlUser";

}
