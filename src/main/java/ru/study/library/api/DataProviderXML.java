package ru.study.library.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.enums.Status;
import ru.study.library.model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static ru.study.library.Constants.*;
import static ru.study.library.Constants.CONFIG_PATH;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LoggerFactory.getLogger(DataProviderXML.class);


    public Status createUser(User user) {
        List<User> users = extract(XML_USER, User.class);
        users.add(user);
        log.debug(users.toString());
        log.info("CREATE_USER");
        return insert(XML_USER, users);
    }

    public  <T> Status insert(String key, List<T> list) {
        try {
            FileWriter writer = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            serializer.write(new WrapperXML<T>(list), writer);
            return SUCCESS;
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return FAIL;
    }

    public  <T> List<T> extract(String key, Class<T> c) {
        try {
            FileReader reader = new FileReader(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            WrapperXML<T> container = serializer.read(WrapperXML.class, reader);
            reader.close();
            if (container.getList() == null) {
                return new ArrayList<>();
            } else {
                return container.getList();
            }
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return new ArrayList<>();
    }
}
