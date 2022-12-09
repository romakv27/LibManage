package ru.study.library.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.Constants;
import ru.study.library.model.HistoryContent;
import ru.study.library.utils.ConfigurationUtil;

import java.io.IOException;

import static ru.study.library.Constants.*;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

/**
 * This class for capturing the state of an object after an operation in NoSQL
 * @author Roman
 * @version 1.0
 */
public class MongoDB {
    /**
     * The constant log.
     */
    private static final Logger log = LoggerFactory.getLogger(MongoDB.class);
    public MongoDB() {
    }
    public MongoDB(String url, String database, String collection) {
    }

    /**
     * This method connecting with Mongo
     * @param object object
     * @param <T>
     * @throws IOException in case of a connection failure
     */
    public <T> void saveObject(T object) throws IOException {
        if (object == null)
            throw new IllegalArgumentException(ERROR_ARG_EMPTY);
        try {
            MongoClient client = MongoClients.create(getConfigurationEntry(MONGO_URL));
            MongoDatabase database = client.getDatabase(getConfigurationEntry(MONGO_DB));
            MongoCollection<Document> collection = database.getCollection(getConfigurationEntry(MONGO_COLLECTION));

            Document document = Document.parse(objectToJSON(object));
            collection.insertOne(document);
            client.close();
            log.info(CONNECT_DB);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }

    }

    /**
     * This method saves the history
     * @param content object
     */
    public static void saveToLog(HistoryContent content) {
        try {
            new MongoDB(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_URL),
                    ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB),
                    ConfigurationUtil.getConfigurationEntry(MONGO_COLLECTION)).saveObject(content);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    /**
     * This method converting an object to JSON
     * @param object object
     * @return converted object
     * @throws JsonProcessingException in case of an operation failure
     */
    private String objectToJSON(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
