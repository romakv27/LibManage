package ru.study.library.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
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

import static java.util.Collections.singletonList;
import static ru.study.library.Constants.*;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderCSV implements IDataProvider{
    public static final Logger log = LoggerFactory.getLogger(DataProviderCSV.class);

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
        List<ArtBook> books = extract(CSV_ARTBOOK, ArtBook.class);
        if (getArtBookById(artBook.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(artBook, FAIL));
            return FAIL;
        }
        books.add(artBook);
        log.debug(books.toString());
        log.info(CREATE_ART);
        saveHistory(createHistoryContent(artBook,Status.SUCCESS));
        return insert(CSV_ARTBOOK, singletonList(artBook), true);
    }

    @Override
    public Optional<ArtBook> getArtBookById(Long id) {
        if (extract(CSV_ARTBOOK, ArtBook.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_ART);
            saveHistory(createHistoryContent(id,Status.SUCCESS));
            return extract(CSV_ARTBOOK, ArtBook.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addScientificBook(Scientific scientific) {
        List<Scientific> books = extract(CSV_SCIENTIFIC, Scientific.class);
        if (getScientificBookById(scientific.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(scientific, FAIL));
            return FAIL;
        }
        books.add(scientific);
        log.debug(books.toString());
        log.info(CREATE_SCIENTIFIC);
        saveHistory(createHistoryContent(scientific,Status.SUCCESS));
        return insert(CSV_SCIENTIFIC, singletonList(scientific), true);
    }

    @Override
    public Optional<Scientific> getScientificBookById(Long id) {
        if (extract(CSV_SCIENTIFIC, Scientific.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_SCIENTIFIC);
            saveHistory(createHistoryContent(id,Status.SUCCESS));
            return extract(CSV_SCIENTIFIC, Scientific.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addChildren(Children children) {
        List<Children> books = extract(CSV_CHILDREN, Children.class);
        if (getChildrenBookById(children.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            return FAIL;
        }
        books.add(children);
        log.debug(books.toString());
        log.info(CREATE_CHILDREN);
        return insert(CSV_CHILDREN, singletonList(children), true);
    }

    @Override
    public Optional<Children> getChildrenBookById(Long id) {
        if (extract(CSV_CHILDREN, Children.class).stream().anyMatch(o -> o.getId().equals(id))){
            log.info(GET_CHILDREN);
            saveHistory(createHistoryContent(id,Status.SUCCESS));
            return extract(CSV_CHILDREN, Children.class)
                    .stream().filter(bean -> bean.getId().equals(id)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addBookToLibrary(Library library) {
        List<Library> list = extract(CSV_LIBRARY, Library.class);
        if (list.stream().anyMatch(o -> o.getId().equals(library.getId()))) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(library, FAIL));
            return FAIL;
        }
        list.add(checkAge(library));
        log.debug(list.toString());
        log.info(ADD_BOOK_TO_LIBRARY);
        saveHistory(createHistoryContent(library, SUCCESS));
        return insert(CSV_LIBRARY, singletonList(library), true);
    }

    @Override
    public Library checkAge(Library userAge) {
        if( userAge.getUser().getAge() >= userAge.getBook().getAgeRestriction()){
            log.info(AGE_EXIST);
            saveHistory(createHistoryContent(userAge, SUCCESS));
            return userAge;
        }
        log.error(ERROR_AGE);
        userAge.setReview(AGE_NOT_EXIST);
        saveHistory(createHistoryContent(userAge, FAIL));
        return userAge;
    }

    @Override
    public Status informationReceipt(String method, Long userId) {
        try{
            switch (method){
                case ALL_USER_REVIEWS:
                case ALL_USER_RATINGS:
                    if(extract(CSV_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))){
                        saveHistory(createHistoryContent(userId, SUCCESS));
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
        saveHistory(createHistoryContent(userId, FAIL));
        return FAIL;
    }

    @Override
    public List<String> allUserReviews(Long userId) {
        try {
            if(extract(CSV_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))) {
                log.info(GET_USER_REVIEW);
                saveHistory(createHistoryContent(userId, SUCCESS));
                return extract(CSV_LIBRARY, Library.class)
                        .stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getReview).collect(Collectors.toList());
            }
        }catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(userId, FAIL));
        return List.of();
    }

    @Override
    public List<Short> allUserRatings(Long userId) {
        try {
            if(extract(CSV_LIBRARY, Library.class).stream().anyMatch(o -> o.getUser().getId().equals(userId))) {
                log.info(GET_USER_RATING);
                saveHistory(createHistoryContent(userId, SUCCESS));
                return extract(CSV_LIBRARY, Library.class)
                        .stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getRating).collect(Collectors.toList());
            }
        }catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(userId, FAIL));
        return List.of();
    }

    @Override
    public Status delBookByTypeAndId(TypeOfBook typeOfBook, Long id) {
        insert(CSV_LIBRARY, extract(CSV_LIBRARY, Library.class)
                .stream().filter(o -> !(o.getBook().getTypeOfBook().equals(typeOfBook) &&
                        o.getBook().getId().equals(id))).collect(Collectors.toList()), false);
        try {
            switch (typeOfBook) {
                case ART -> {
                    List<ArtBook> artBookList = extract(CSV_ARTBOOK, ArtBook.class);
                    if (getArtBookById(id).isPresent()) {
                        artBookList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_ART);
                        return insert(CSV_ARTBOOK, artBookList, false);
                    }
                    return FAIL;
                }
                case SCIENTIFIC -> {
                    List<Scientific> scientificList = extract(CSV_SCIENTIFIC, Scientific.class);
                    if (getScientificBookById(id).isPresent()) {
                        scientificList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_SCIENTIFIC);
                        return insert(CSV_SCIENTIFIC, scientificList, false);
                    }
                    return FAIL;
                }
                case CHILDREN -> {
                    List<Children> childrenList = extract(CSV_CHILDREN, Children.class);
                    if (getChildrenBookById(id).isPresent()) {
                        childrenList.removeIf(bean -> bean.getId().equals(id));
                        log.info(DELETE_CHILDREN);
                        return insert(CSV_CHILDREN, childrenList, false);
                    }
                    return FAIL;
                }
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
        saveHistory(createHistoryContent(book,Status.SUCCESS));
        return insert(CSV_LIBRARY, extract(CSV_LIBRARY, Library.class)
                .stream().filter(o -> !(o.getBook().getId().equals(book.getId()) && o.getBook().getTypeOfBook()
                        .equals(book.getTypeOfBook()))).collect(Collectors.toList()), false);
    }

    @Override
    public Status createUser(User user) {
        if (getUserById(user.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(user,Status.FAIL));
            return FAIL;
        }
        log.info(CREATE_USER);
        saveHistory(createHistoryContent(user,Status.SUCCESS));
        return insert(CSV_USER, Collections.singletonList(user),true);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        if (extract(CSV_USER, User.class).stream().anyMatch(o -> o.getId().equals(userId))) {
            saveHistory(createHistoryContent(userId,Status.SUCCESS));
            return extract(CSV_USER, User.class)
                    .stream().filter(user -> user.getId().equals(userId)).findFirst();
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(userId,Status.FAIL));
        return Optional.empty();
    }

    @Override
    public Status updateUser(User user) {
        List<User> users = extract(CSV_USER, User.class);
        if (users.stream().noneMatch(bean -> bean.getId().equals(user.getId()))) {
            log.error(ERROR_ID_NOT_EXIST);
            saveHistory(createHistoryContent(users, FAIL));
            return FAIL;
        }
        if (getUserById(user.getId()).isPresent()) {
            deleteUserById(user.getId());
        }
        log.info(UPDATE_USER);
        saveHistory(createHistoryContent(users,Status.SUCCESS));
        return createUser(user);
    }

    @Override
    public Status deleteUserById(Long userId) {
        List<User> users = extract(CSV_USER, User.class);
        if (getUserById(userId).isPresent()) {
            users.removeIf(bean -> bean.getId().equals(userId));
            log.info(DELETE_USER);
            saveHistory(createHistoryContent(users,Status.SUCCESS));
            return insert(CSV_USER, users, false);
        }
        saveHistory(createHistoryContent(users, FAIL));
        return FAIL;
    }

    public <T> Status insert(String key, List<T> list, boolean append){
        try {
            FileWriter writer = new FileWriter(getConfigurationEntry(key), append);
            CSVWriter csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).withApplyQuotesToAll(false).build();
            beanToCsv.write(list);
            csvWriter.close();
            return SUCCESS;
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return FAIL;
    }

    public <T> List <T> extract(String key, Class<T> c){
        List <T> list = new ArrayList<>();
        try {
            FileReader reader = new FileReader(getConfigurationEntry(key));
            CSVReader csvReader = new CSVReader(reader);
            list = new CsvToBeanBuilder<T>(csvReader).withType(c).build().parse();
            csvReader.close();
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return list;
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
