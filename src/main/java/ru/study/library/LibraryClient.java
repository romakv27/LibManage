package ru.study.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.library.api.DataProviderCSV;
import ru.study.library.api.DataProviderJDBC;
import ru.study.library.api.DataProviderXML;
import ru.study.library.api.IDataProvider;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.*;

import static ru.study.library.Constants.*;

public class LibraryClient {
    private static final Logger log = LoggerFactory.getLogger(LibraryClient.class);

    public static void main(String[] args) {
        try {
            IDataProvider provider = selectDataProvider(args[0]);
            switch (args[1]) {
                case ADD_BOOK:
                    log.info(addBook(provider, args));
                    break;
                case LIBRARY:
                    log.info(library(provider, args));
                    break;
                case INFORMATION_RECEIPT:
                    log.info(informationReceipt(provider, args));
                    break;
                case DELETE_BOOK:
                    log.info(delBook(provider, args));
                    break;
                case GET_BOOK:
                    log.info(getBook(provider, args));
                    break;
                case USER:
                    log.info(user(provider, args));
                    break;
                default:
                    log.error(ERROR_COMMAND);
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public static IDataProvider selectDataProvider(String str) {
        switch (str) {
            case XML:
                return new DataProviderXML();
            case CSV:
                return new DataProviderCSV();
            case JDBC:
                return new DataProviderJDBC();
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String addBook(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case ART_BOOK:
                return provider.addArtBook(new ArtBook(Long.parseLong(s[3]), s[4], s[5], Integer.parseInt(s[6]),
                        Integer.parseInt(s[7]), s[8], Boolean.parseBoolean(s[9]))).name();
            case SCIENTIFIC_BOOK:
                return provider.addScientificBook(new Scientific(Long.parseLong(s[3]), s[4], s[5],
                        Integer.parseInt(s[6]), Integer.parseInt(s[7]), s[8], Boolean.parseBoolean(s[9]))).name();
            case CHILDREN_BOOK:
                return provider.addChildren(new Children(Long.parseLong(s[3]), s[4], s[5], Integer.parseInt(s[6]),
                        Integer.parseInt(s[7]), s[8], Boolean.parseBoolean(s[9]), Boolean.parseBoolean(s[10]),
                        Boolean.parseBoolean(s[11]))).name();
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String library(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case ADD_BOOK_LIBRARY:
                return provider.addBookToLibrary(new Library(Long.parseLong(s[3]),
                        provider.getBookByTypeAndId(TypeOfBook.valueOf(s[4]), Long.parseLong(s[5])).orElseThrow(() -> {
                            throw new UnsupportedOperationException("Book is not exists");
                        }),
                        provider.getUserById(Long.parseLong(s[6])).orElseThrow(() -> {
                            throw new UnsupportedOperationException("User is not exists");
                        }),
                        s[7], Short.parseShort(s[8])
                )).name();
            case DEL_BOOK_IN_LIBRARY:
                return provider.delBookInLibrary(provider.getBookByTypeAndId(TypeOfBook.valueOf(s[3]), Long.parseLong(s[4]))
                        .orElseThrow(() -> {throw new UnsupportedOperationException("Book is not exists");})).name();
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String informationReceipt(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case ALL_USER_RATINGS:
                return provider.allUserRatings(Long.parseLong(s[3])).toString();
            case ALL_USER_REVIEWS:
                return provider.allUserReviews(Long.parseLong(s[3])).toString();
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String getBook(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case ART_BOOK:
                return provider.getArtBookById(Long.parseLong(s[3])).map(ArtBook::toString).orElse("ArtBook is not exists");
            case SCIENTIFIC_BOOK:
                return provider.getScientificBookById(Long.parseLong(s[3])).map(Scientific::toString).orElse("Scientific is not exists");
            case CHILDREN_BOOK:
                return provider.getChildrenBookById(Long.parseLong(s[3])).map(Children::toString).orElse("ChildrenBook is not exists");
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String delBook(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case ART_BOOK:
                return provider.delBookByTypeAndId(TypeOfBook.ART, Long.parseLong(s[3])).name();
            case SCIENTIFIC_BOOK:
                return provider.delBookByTypeAndId(TypeOfBook.SCIENTIFIC, Long.parseLong(s[3])).name();
            case CHILDREN_BOOK:
                return provider.delBookByTypeAndId(TypeOfBook.CHILDREN, Long.parseLong(s[3])).name();
            default:
                throw new IllegalStateException(ERROR_COMMAND);
        }
    }

    public static String user(IDataProvider provider, String[] s) {
        switch (s[2]) {
            case USER_CREATE:
                return provider.createUser(new User(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).name();
            case USER_GET:
                return provider.getUserById(Long.parseLong(s[3])).map(User::toString).orElse("User not exists");
            case USER_UPD:
                return provider.updateUser(new User(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).name();
            case USER_DEL:
                return provider.deleteUserById(Long.parseLong(s[3])).name();
            default:
                throw new IllegalStateException("ERROR_COMMAND");
        }
    }
}
