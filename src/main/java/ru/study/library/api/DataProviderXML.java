package ru.study.library.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.Constants;
import ru.study.library.enums.Status;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.study.library.Constants.*;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LoggerFactory.getLogger(DataProviderXML.class);

    @Override
    public Status addBook(String method, ArtBook artBook, Scientific scientific, Children children) {
        try{
            switch (method){
                case ART:
                    if (getArtBookById(artBook.getId()).isEmpty()){
                        return SUCCESS;
                    }
                    log.error(ERROR_ID_EXIST);
                    break;
                case SCIENTIFIC:
                    if (getScientificBookById(scientific.getId()).isEmpty()){
                        return SUCCESS;
                    }
                    log.error(ERROR_ID_EXIST);
                    break;
                case CHILDREN:
                    if (getChildrenBookById(children.getId()).isEmpty()){
                        return SUCCESS;
                    }
                    log.error(ERROR_ID_EXIST);
                    break;
            }}
        catch (Exception e) {
            log.error(String.valueOf(e));
        }log.error(ERROR_COMMAND);
        return FAIL;
    }

    @Override
    public Status addArtBook(ArtBook artBook) {
        List<ArtBook> books = extract(XML_ARTBOOK, ArtBook.class);
        if (getArtBookById(artBook.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            return FAIL;
        }
        books.add(artBook);
        log.debug(books.toString());
        log.info(CREATE_ART);
        return insert(XML_ARTBOOK, books);
    }

    @Override
    public Optional<ArtBook> getArtBookById(Long id) {
        if (extract(XML_ARTBOOK, ArtBook.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_ART);
            return extract(XML_ARTBOOK, ArtBook.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        return Optional.empty();
    }

    @Override
    public Status addScientificBook(Scientific scientific) {
        List<Scientific> books = extract(XML_SCIENTIFIC, Scientific.class);
        if (getScientificBookById(scientific.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            return FAIL;
        }
        books.add(scientific);
        log.debug(books.toString());
        log.info(CREATE_SCIENTIFIC);
        return insert(XML_SCIENTIFIC, books);
    }

    @Override
    public Optional<Scientific> getScientificBookById(Long id) {
        if (extract(XML_SCIENTIFIC, Scientific.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_SCIENTIFIC);
            return extract(XML_SCIENTIFIC, Scientific.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        return Optional.empty();
    }

    @Override
    public Status addChildren(Children children) {
        List<Children> books = extract(XML_CHILDREN, Children.class);
        if (getChildrenBookById(children.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            return FAIL;
        }
        books.add(children);
        log.debug(books.toString());
        log.info(CREATE_CHILDREN);
        return insert(XML_CHILDREN, books);
    }

    @Override
    public Optional<Children> getChildrenBookById(Long id) {
        if (extract(XML_CHILDREN, Children.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_CHILDREN);
            return extract(XML_CHILDREN, Children.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        return Optional.empty();
    }

    @Override
    public Status addBookToLibrary(Library library) {
        List<Library> list = extract(XML_LIBRARY, Library.class);
        if (list.stream().anyMatch(o -> o.getId().equals(library.getId()))) {
            log.error(ERROR_ID_EXIST);
            return FAIL;
        }
        list.add(checkAge(library));
        log.debug(list.toString());
        log.info(ADD_BOOK_TO_LIBRARY);
        return insert(XML_LIBRARY, list);
    }

    @Override
    public Library checkAge(Library userAge) {
        if( userAge.getUser().getAge() >= userAge.getBook().getAgeRestriction()){
            log.info(AGE_EXIST);
            return userAge;
        }
        log.error(ERROR_AGE);
        userAge.setReview(AGE_NOT_EXIST);
        return userAge;
    }

    @Override
    public Status informationReceipt(String method, Long userId) {
        try{
            switch (method){
                case ALL_USER_REVIEWS:
                case ALL_USER_RATINGS:
                    if(extract(XML_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))){
                        return SUCCESS;
                    }
                    break;
            }
        }catch (Exception e) {
            log.error(String.valueOf(e));
            log.error(ERROR_COMMAND);
            return FAIL;
        }
        log.error(ERROR_COMMAND);
        return FAIL;
    }

    @Override
    public List<String> allUserReviews(Long userId) {
        try {
            if(extract(XML_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))) {
                log.info(GET_USER_REVIEW);
                return extract(XML_LIBRARY, Library.class)
                        .stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getReview).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.error(ERROR_ID_NOT_EXIST);
        return List.of();
    }

    @Override
    public List<Short> allUserRatings(Long userId) {
        try {
            if(extract(XML_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))) {
                log.info(GET_USER_RATING);
                return extract(XML_LIBRARY, Library.class)
                        .stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getRating).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.error(ERROR_ID_NOT_EXIST);
        return List.of();
    }

    @Override
    public Status delBookByTypeAndId(TypeOfBook typeOfBook, Long id) {
        insert(XML_LIBRARY, extract(XML_LIBRARY, Library.class)
                .stream().filter(o -> !(o.getBook().getTypeOfBook().equals(typeOfBook) &&
                        o.getBook().getId().equals(id))).collect(Collectors.toList()));
        try {
            switch (typeOfBook) {
                case ART -> {
                    List<ArtBook> artBookList = extract(XML_ARTBOOK, ArtBook.class);
                    if (getArtBookById(id).isPresent()) {
                        artBookList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_ART);
                        return insert(XML_ARTBOOK, artBookList);
                    }
                    return FAIL;
                }
                case SCIENTIFIC -> {
                    List<Scientific> scientificList = extract(XML_SCIENTIFIC, Scientific.class);
                    if (getScientificBookById(id).isPresent()) {
                        scientificList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_SCIENTIFIC);
                        return insert(XML_SCIENTIFIC, scientificList);
                    }
                    return FAIL;
                }
                case CHILDREN -> {
                    List<Children> childrenList = extract(XML_CHILDREN, Children.class);
                    if (getChildrenBookById(id).isPresent()) {
                        childrenList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_CHILDREN);
                        return insert(XML_CHILDREN, childrenList);
                    }
                    return FAIL;
                }
                default -> throw new IllegalStateException("Unexpected value: " + typeOfBook);
            }
        }catch (Exception  e) {
            log.error(String.valueOf(e));
        }
        return FAIL;
    }

    @Override
    public Optional<? extends Book> getBookByTypeAndId(TypeOfBook typeOfBook, Long id) {
        try{switch (typeOfBook) {
            case SCIENTIFIC:
                return getScientificBookById(id);
            case ART:
                return getArtBookById(id);
            case CHILDREN:
                return getChildrenBookById(id);
        }}catch (Exception  e) {
            log.error(String.valueOf(e));
        }
        log.error(ERROR_ID_NOT_EXIST);
        return Optional.empty();
    }

    @Override
    public Status delBookInLibrary(Book book) {
        return insert(XML_LIBRARY, extract(XML_LIBRARY, Library.class)
                .stream().filter(o -> !(o.getBook().getId().equals(book.getId()) && o.getBook().getTypeOfBook()
                        .equals(book.getTypeOfBook()))).collect(Collectors.toList()));
    }

    @Override
    public Status createUser(User user) {
        List<User> users = extract(XML_USER, User.class);
        if (getUserById(user.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(users,Status.FAIL));
            return FAIL;
        }
        users.add(user);
        log.debug(users.toString());
        log.info(CREATE_USER);
        saveHistory(createHistoryContent(users,Status.SUCCESS));
        return insert(XML_USER, users);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        if (extract(XML_USER, User.class).stream().anyMatch(o -> o.getId().equals(userId))){
            log.info(GET_USER);
            saveHistory(createHistoryContent(userId,Status.SUCCESS));
            return extract(XML_USER, User.class)
                    .stream().filter(bean -> bean.getId().equals(userId)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(userId,Status.FAIL));
        return Optional.empty();
    }

    @Override
    public Status updateUser(User user) {
        List<User> users = extract(XML_USER, User.class);
        if (users.stream().noneMatch(bean -> bean.getId().equals(user.getId()))) {
            log.error(ERROR_ID_NOT_EXIST);
            saveHistory(createHistoryContent(users, FAIL));
            return FAIL;
        }
        users.removeIf(bean -> bean.getId().equals(user.getId()));
        users.add(user);
        log.info(UPDATE_USER);
        log.debug(users.toString());
        saveHistory(createHistoryContent(users,Status.SUCCESS));
        return insert(XML_USER, users);
    }

    @Override
    public Status deleteUserById(Long userId) {
        List<User> users = extract(XML_USER, User.class);
        if (getUserById(userId).isPresent()) {
            users.removeIf(bean -> bean.getId().equals(userId));
            log.info(DELETE_USER);
            saveHistory(createHistoryContent(users,Status.SUCCESS));
            return insert(XML_USER, users);
        }
        saveHistory(createHistoryContent(users, FAIL));
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

    protected HistoryContent createHistoryContent(Object object, Status status) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
        return new HistoryContent(UUID.randomUUID(),
                stackTrace.getClassName(),
                System.currentTimeMillis(),
                Constants.ACTOR,
                stackTrace.getMethodName(),
                object,
                status);
    }

    protected void saveHistory(HistoryContent historyContent){
        MongoDB.saveToLog(historyContent);
    }
}
