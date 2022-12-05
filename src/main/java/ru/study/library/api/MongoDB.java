package ru.study.library.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.Constants;
import ru.study.library.model.HistoryContent;
import ru.study.library.utils.ConfigurationUtil;

import java.io.IOException;

import static ru.study.library.Constants.*;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class MongoDB {
    private static final Logger log = LoggerFactory.getLogger(MongoDB.class);
    public MongoDB() {
    }
    public MongoDB(String url, String database, String collection) {
    }

//    public <T> void saveObject(T object) throws IOException {
//        if (object == null)
//            throw new IllegalArgumentException("Constants.ERROR_ARG_EMPTY");
//        try {
//            MongoClient mongoDB = MongoClients.create(getConfigurationEntry(Constants.MONGO_URL));
//            InsertOneResult insertOneResult = mongoDB.getDatabase(getConfigurationEntry(Constants.MONGO_DB))
//                    .getCollection(getConfigurationEntry(MONGO_COLLECTION))
//                    .insertOne(Document.parse(objectToJSON(object)));
//            mongoDB.close();
//            System.out.println("ok");
//            if (!insertOneResult.wasAcknowledged())
//                throw new IllegalArgumentException();
//        } catch (Exception e) {
//            log.error(String.valueOf(e));
//            throw e;
//        }
//    }

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

    public static void saveToLog(HistoryContent content) {
        try {
            new MongoDB(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_URL),
                    ConfigurationUtil.getConfigurationEntry(Constants.MONGO_DB),
                    ConfigurationUtil.getConfigurationEntry(MONGO_COLLECTION)).saveObject(content);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    private String objectToJSON(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
