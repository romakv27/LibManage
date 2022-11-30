package ru.study.library;

import java.util.Properties;

public class Constants {

    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    public static final String CONFIG_PATH = System.getProperty("config");
    public static final Properties configuration = new Properties();

    //Mongo
    public static final String MONGO_URL = "mongoUrl";
    public static final String MONGO_DB = "mongoDB";
    public static final String MONGO_COLLECTION = "mongoCollection";
    public static final String ACTOR = "System";

    // Data providers
    public static final String XML = "xml";

    // API - base methods names
    public static final String USER = "user";

    // API - extend methods names
    public static final String USER_CREATE = "create";
    public static final String USER_GET = "get";
    public static final String USER_UPD = "upd";
    public static final String USER_DEL = "del";

    public static final String ALL_USER_REVIEWS = "all_user_reviews";
    public static final String ALL_USER_RATINGS= "all_user_ratings";

    public static final String ART = "ART";
    public static final String SCIENTIFIC= "SCIENTIFIC";
    public static final String CHILDREN= "CHILDREN";

    //FILES
    public static final String XML_ARTBOOK = "xmlArtBook";
    public static final String XML_SCIENTIFIC = "xmlScientific";
    public static final String XML_CHILDREN = "xmlChildren";
    public static final String XML_LIBRARY = "xmlLibrary";
    public static final String XML_USER = "xmlUser";

    //Massages ERROR
    public static final String ERROR_ID_EXIST = "This id exist";
    public static final String ERROR_ID_NOT_EXIST = "This id not exist";
    public static final String ERROR_COMMAND = "Incorrect entry";
    public static final String ERROR_AGE = "Age is not acceptable";
    public static final String ERROR_ARG_EMPTY = "Argument is empty";

    //Massages INFO
    public static final String CREATE_USER = "User is created";
    public static final String GET_USER = "User is displayed";
    public static final String UPDATE_USER = "User is update";
    public static final String DELETE_USER = "User was deleted";

    public static final String CREATE_ART = "Art book is created";
    public static final String GET_ART = "Art book is displayed";
    public static final String DELETE_ART = "Art book was deleted";

    public static final String CREATE_SCIENTIFIC = "Scientific book is created";
    public static final String GET_SCIENTIFIC = "Scientific book is displayed";
    public static final String DELETE_SCIENTIFIC = "Scientific book was deleted";

    public static final String CREATE_CHILDREN = "Children book is created";
    public static final String GET_CHILDREN = "Children book is displayed";
    public static final String DELETE_CHILDREN = "Children book was deleted";

    public static final String ADD_BOOK_TO_LIBRARY = "Book added to Library";
    public static final String GET_USER_RATING = "Book ratings by user is displayed";
    public static final String GET_USER_REVIEW = "Book review by user is displayed";
    public static final String AGE_EXIST = "Age is appropriate ";
    public static final String AGE_NOT_EXIST = "You are under the age limit";

    public static final String CONNECT_DB = "Connected to DB";

}
