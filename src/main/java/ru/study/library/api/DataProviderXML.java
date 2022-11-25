package ru.study.library.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.enums.Status;
import ru.study.library.model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.study.library.Constants.*;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LoggerFactory.getLogger(DataProviderXML.class);

    @Override
    public Status createUser(User user) {
        List<User> users = extract(XML_USER, User.class);
        if (getUserById(user.getId()).isPresent()) {
            log.error("ERROR_ID_EXIST");
            return FAIL;
        }
        users.add(user);
        log.debug(users.toString());
        log.info("CREATE_USER");
        return insert(XML_USER, users);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        if (extract(XML_USER, User.class).stream().anyMatch(o -> o.getId().equals(userId))){
            log.info("GET_USER");
            return extract(XML_USER, User.class)
                    .stream().filter(bean -> bean.getId().equals(userId)).findFirst();
        }
        log.error("ERROR_ID_NOT_EXIST");
        return Optional.empty();
    }

    @Override
    public Status updateUser(User user) {
        List<User> users = extract(XML_USER, User.class);
        if (users.stream().noneMatch(bean -> bean.getId().equals(user.getId()))) {
            log.error("ERROR_ID_NOT_EXIST");
            return FAIL;
        }
        users.removeIf(bean -> bean.getId().equals(user.getId()));
        users.add(user);
        log.info("UPDATE_USER");
        log.debug(users.toString());
        return insert(XML_USER, users);
    }

    @Override
    public Status deleteUserById(Long userId) {
        List<User> users = extract(XML_USER, User.class);
        if (getUserById(userId).isPresent()) {
            users.removeIf(bean -> bean.getId().equals(userId));
            log.info("DELETE_USER");
            return insert(XML_USER, users);
        }
        return FAIL;
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
                container.setList(new ArrayList<>());
            } else {
                return container.getList();
            }
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return new ArrayList<>();
    }
}
