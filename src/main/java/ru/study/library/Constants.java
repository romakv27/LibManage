package ru.study.library;

import java.util.Properties;

public class Constants {

    public static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    public static final String CONFIG_PATH = System.getProperty("config");
    public static final Properties configuration = new Properties();

    //JDBC Connect
    public static final String DB_DRIVER = "dbDriver";
    public static final String DB_URL = "dbUrl";
    public static final String DB_USER = "dbUser";
    public static final String DB_PASSWORD = "dbPassword";

    //Mongo Connect
    public static final String MONGO_URL = "mongoUrl";
    public static final String MONGO_DB = "mongoDB";
    public static final String MONGO_COLLECTION = "mongoCollection";
    public static final String ACTOR = "System";

    // Data providers
    public static final String XML = "xml";
    public static final String CSV = "csv";
    public static final String JDBC = "jdbc";

    // API - base methods names
    public static final String USER = "user";
    public static final String LIBRARY = "library";
    public static final String ADD_BOOK = "addBook";
    public static final String INFORMATION_RECEIPT = "informationReceipt";
    public static final String DELETE_BOOK="deleteBook";
    public static final String GET_BOOK="getBook";

    // API - extend methods names
    public static final String USER_CREATE = "create";
    public static final String USER_GET = "get";
    public static final String USER_UPD = "upd";
    public static final String USER_DEL = "del";

    public static final String ADD_BOOK_LIBRARY = "addBook";
    public static final String DEL_BOOK_IN_LIBRARY = "del";

    public static final String ART_BOOK = "art";
    public static final String SCIENTIFIC_BOOK = "scientific";
    public static final String CHILDREN_BOOK = "children";

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

    public static final String CSV_USER = "csvUser";
    public static final String CSV_ARTBOOK = "csvArtBook";
    public static final String CSV_SCIENTIFIC = "csvScientific";
    public static final String CSV_CHILDREN = "csvChildren";
    public static final String CSV_LIBRARY = "csvLibrary";

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

    //SQL
    public static final String SQL_CREATE_USER = "INSERT INTO USER(id, name, age) VALUES(%d, '%s', %d);";
    public static final String SQL_GET_USER_BY_ID = "SELECT id, name, age FROM USER WHERE id=%d;";
    public static final String SQL_UPD_USER = "UPDATE USER SET name='%s', age=%d WHERE id=%d;";
    public static final String SQL_DEL_USER = "DELETE FROM USER WHERE id=%d;";

    public static final String SQL_CREATE_ART_BOOK = "INSERT INTO ART_BOOK(id, title, author, numberOfPages, ageRestriction, typeOfBook, genre, comics) VALUES(%d, '%s', '%s', %d, %d, '%s', '%s', %d);";
    public static final String SQL_CREATE_SCIENTIFIC_BOOK = "INSERT INTO SCIENTIFIC_BOOK(id, title, author, numberOfPages, ageRestriction, typeOfBook, direction, forStudy) VALUES(%d, '%s', '%s', %d, %d, '%s', '%s', %d);";
    public static final String SQL_CREATE_CHILDREN_BOOK = "INSERT INTO CHILDREN_BOOK(id, title, author, numberOfPages, ageRestriction, typeOfBook, genre, comics, educational, interactive) VALUES(%d, '%s', '%s', %d, %d, '%s', '%s', %d, %d, %d);";

    public static final String SQL_GET_ART_BOOK_BY_ID = "SELECT id, title, author, numberOfPages, ageRestriction, typeOfBook, genre, comics FROM ART_BOOK WHERE id=%d;";
    public static final String SQL_GET_SCIENTIFIC_BOOK_BY_ID = "SELECT id, title, author, numberOfPages, ageRestriction, typeOfBook, direction, forStudy FROM SCIENTIFIC_BOOK WHERE id=%d;";
    public static final String SQL_GET_CHILDREN_BOOK_BY_ID = "SELECT id, title, author, numberOfPages, ageRestriction, typeOfBook, genre, comics, educational, interactive FROM CHILDREN_BOOK WHERE id=%d;";

    public static final String SQL_DEL_LIBRARY = "DELETE FROM LIBRARY WHERE book=%d AND typeOfBook='%s';";
    public static final String SQL_DEL_ART_BOOK = "DELETE FROM ART_BOOK WHERE id=%d;";
    public static final String SQL_DEL_SCIENTIFIC_BOOK = "DELETE FROM SCIENTIFIC_BOOK WHERE id=%d;";
    public static final String SQL_DEL_CHILDREN_BOOK = "DELETE FROM CHILDREN_BOOK WHERE id=%d;";

    public static final String SQL_ADD_BOOK_TO_LIBRARY = "INSERT INTO LIBRARY(id, book, user, review, rating, typeOfBook) VALUES(%d, %d, %d, '%s', %d, '%s');";
    public static final String SQL_GET_ALL_LIBRARY = "SELECT id, book, user, review, rating, typeOfBook FROM LIBRARY;";

}
