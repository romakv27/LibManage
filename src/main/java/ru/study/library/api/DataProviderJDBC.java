package ru.study.library.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.Constants;
import ru.study.library.enums.Status;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.*;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static ru.study.library.Constants.*;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;
import static ru.study.library.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJDBC implements IDataProvider{
    public static final Logger log = LoggerFactory.getLogger(DataProviderJDBC.class);

    /**
     * This method connecting with DB
     * @return connection
     * @throws Exception in case of a connection failure
     */
    protected Connection connection() throws Exception {
        return DriverManager.getConnection(
                getConfigurationEntry(DB_URL),
                getConfigurationEntry(DB_USER),
                getConfigurationEntry(DB_PASSWORD));
    }

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
        if (getArtBookById(artBook.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(artBook, FAIL));
            return FAIL;
        }
        log.info(CREATE_ART);
        saveHistory(createHistoryContent(artBook,Status.SUCCESS));
        return insert(String.format(SQL_CREATE_ART_BOOK, artBook.getId(), artBook.getTitle(), artBook.getAuthor(),
                artBook.getNumberOfPages(), artBook.getAgeRestriction(), artBook.getTypeOfBook().name(), artBook.getGenre(),
                artBook.getComics()?1:0));
    }

    @Override
    public Optional<ArtBook> getArtBookById(Long id) {
        ResultSet query = extract(String.format(SQL_GET_ART_BOOK_BY_ID, id));
        try {
            if (query != null && query.next()) {
                log.info(GET_ART);
                saveHistory(createHistoryContent(id,Status.SUCCESS));
                return Optional.of(new ArtBook(query.getLong(1), query.getString(2), query.getString(3),
                        query.getInt(4), query.getInt(5), query.getString(7), query.getInt(8) == 1));
            }
        } catch (SQLException exception) {
            log.error(String.valueOf(exception));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addScientificBook(Scientific scientific) {
        if (getScientificBookById(scientific.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(scientific, FAIL));
            return FAIL;
        }
        log.info(CREATE_SCIENTIFIC);
        saveHistory(createHistoryContent(scientific,Status.SUCCESS));
        return insert(String.format(SQL_CREATE_SCIENTIFIC_BOOK, scientific.getId(), scientific.getTitle(),
                scientific.getAuthor(), scientific.getNumberOfPages(), scientific.getAgeRestriction(),
                scientific.getTypeOfBook().name(), scientific.getDirection(), scientific.getForStudy()?1:0));
    }

    @Override
    public Optional<Scientific> getScientificBookById(Long id) {
        ResultSet query = extract(String.format(SQL_GET_SCIENTIFIC_BOOK_BY_ID, id));
        try {
            if (query != null && query.next()) {
                log.info(GET_SCIENTIFIC);
                saveHistory(createHistoryContent(id,Status.SUCCESS));
                return Optional.of(new Scientific(query.getLong(1), query.getString(2),
                        query.getString(3), query.getInt(4), query.getInt(5),
                        query.getString(7), query.getInt(8) == 1));
            }
        } catch (SQLException exception) {
            log.error(String.valueOf(exception));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addChildren(Children children) {
        if (getChildrenBookById(children.getId()).isPresent()) {
            log.error(ERROR_ID_EXIST);
            saveHistory(createHistoryContent(children, FAIL));
            return FAIL;
        }
        log.info(CREATE_CHILDREN);
        saveHistory(createHistoryContent(children, SUCCESS));
        return insert(String.format(SQL_CREATE_CHILDREN_BOOK, children.getId(), children.getTitle(),
                children.getAuthor(), children.getNumberOfPages(), children.getAgeRestriction(),
                children.getTypeOfBook().name(), children.getGenre(), children.getComics()?1:0,
                children.getEducational()?1:0, children.getInteractive()?1:0));
    }

    @Override
    public Optional<Children> getChildrenBookById(Long id) {
        ResultSet query = extract(String.format(SQL_GET_CHILDREN_BOOK_BY_ID, id));
        try {
            if (query != null && query.next()) {
                log.info(GET_CHILDREN);
                saveHistory(createHistoryContent(id,Status.SUCCESS));
                return Optional.of(new Children(query.getLong(1), query.getString(2),
                        query.getString(3), query.getInt(4), query.getInt(5),
                        query.getString(7), query.getInt(8) == 1,
                        query.getInt(9) == 1, query.getInt(10) == 1));
            }
        } catch (SQLException exception) {
            log.error(String.valueOf(exception));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(id, FAIL));
        return Optional.empty();
    }

    @Override
    public Status addBookToLibrary(Library library) {
        return insert(String.format(SQL_ADD_BOOK_TO_LIBRARY, library.getId(), library.getBook().getId(),
                library.getUser().getId(), library.getReview(), library.getRating(), library.getBook().getTypeOfBook().name()));
    }

    @Override
    public Library checkAge(Library userAge) {
        if( userAge.getUser().getAge() >= userAge.getBook().getAgeRestriction()){
            log.info(AGE_EXIST);
            saveHistory(createHistoryContent(userAge, FAIL));
            return userAge;
        }
        log.error(ERROR_AGE);
        userAge.setReview(AGE_NOT_EXIST);
        saveHistory(createHistoryContent(userAge, SUCCESS));
        return userAge;
    }

    @Override
    public Status informationReceipt(String method, Long userId) {
        try{
            switch (method){
                case ALL_USER_REVIEWS:
                case ALL_USER_RATINGS:
                    if(getAllLibrary().stream().anyMatch(o -> o.getUser().getId().equals(userId))){
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
        return getAllLibrary().stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getReview).collect(Collectors.toList());
    }

    @Override
    public List<Short> allUserRatings(Long userId) {
        return getAllLibrary().stream().filter(o -> o.getUser().getId().equals(userId)).map(Library::getRating).collect(Collectors.toList());
    }

    protected List<Library> getAllLibrary() {
        ResultSet query = extract(SQL_GET_ALL_LIBRARY);
        List<Library> list = new ArrayList<>();
        try {
            while (query != null && query.next()) {
                Library obj = new Library(query.getLong(1), query.getString(4), query.getShort(5));
                obj.setUser(getUserById(query.getLong(3)).orElseThrow(() -> {
                    throw new UnsupportedOperationException("User is not exists");
                }));
                list.add(obj);
            }
        } catch (SQLException exception) {
            log.error(String.valueOf(exception));
        }
        return list;
    }

    @Override
    public Status delBookByTypeAndId(TypeOfBook typeOfBook, Long id) {
        insert(String.format(SQL_DEL_LIBRARY, id, typeOfBook.name()));
        try {
            switch (typeOfBook) {
                case ART -> {
                    if (getArtBookById(id).isPresent()) {
                        log.info(DELETE_ART);
                        return insert(String.format(SQL_DEL_ART_BOOK, id));
                    }
                    return FAIL;
                }
                case SCIENTIFIC -> {
                    if (getScientificBookById(id).isPresent()) {
                        log.info(DELETE_SCIENTIFIC);
                        return insert(String.format(SQL_DEL_SCIENTIFIC_BOOK, id));
                    }
                    return FAIL;
                }
                case CHILDREN -> {
                    if (getChildrenBookById(id).isPresent()) {
                        log.info(DELETE_CHILDREN);
                        return insert(String.format(SQL_DEL_CHILDREN_BOOK, id));
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
        try{
            switch (typeOfBook) {
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
        return insert(String.format(SQL_DEL_LIBRARY, book.getId(), book.getTypeOfBook().name()));
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
        return insert(String.format(SQL_CREATE_USER, user.getId(), user.getName(), user.getAge()));
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        ResultSet query = extract(String.format(SQL_GET_USER_BY_ID, userId));
        try {
            if (query != null && query.next()) {
                saveHistory(createHistoryContent(userId,Status.SUCCESS));
                return Optional.of(new User(query.getLong(1), query.getString(2), query.getInt(3)));
            }
        } catch (SQLException exception) {
            log.error(String.valueOf(exception));
        }
        saveHistory(createHistoryContent(userId,Status.FAIL));
        return Optional.empty();
    }

    @Override
    public Status updateUser(User user) {
        if (getUserById(user.getId()).isPresent()) {
            log.info(UPDATE_USER);
            saveHistory(createHistoryContent(user,Status.SUCCESS));
            return insert(String.format(SQL_UPD_USER, user.getName(), user.getAge(), user.getId()));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(user, FAIL));
        return FAIL;
    }

    @Override
    public Status deleteUserById(Long userId) {
        if (getUserById(userId).isPresent()) {
            log.info(DELETE_USER);
            saveHistory(createHistoryContent(userId,Status.SUCCESS));
            return insert(String.format(SQL_DEL_USER, userId));
        }
        log.error(ERROR_ID_NOT_EXIST);
        saveHistory(createHistoryContent(userId, FAIL));
        return FAIL;
    }

    protected Status insert(String sql) {
        try {
            PreparedStatement statement = connection().prepareStatement(sql);
            statement.executeUpdate();
            connection().close();
            return SUCCESS;
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return FAIL;
    }

    protected ResultSet extract(String sql) {
        try {
            PreparedStatement statement = connection().prepareStatement(sql);
            connection().close();
            return statement.executeQuery();
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return null;
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
