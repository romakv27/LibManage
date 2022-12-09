package ru.study.library.api;

import ru.study.library.enums.Status;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.*;

import java.util.List;
import java.util.Optional;

/**
 * This interface for implementation of methods, for each data provider
 * @author Roman
 * @version 1.0
 * @since 1.0
 */
public interface IDataProvider {

    /**
     * The method allows you to add all types of books.
     * @param method user action
     * @param artBook fiction book
     * @param scientific scientific book
     * @param children children's book
     * @return Status
     */
    Status addBook(String method, ArtBook artBook, Scientific scientific, Children children);

    /**
     * The method allows you to add fiction books.
     * @param artBook fiction book
     * @return Status
     */
    Status addArtBook(ArtBook artBook);

    /**
     * The method allows you to review fiction books.
     * @param id book identifier
     * @return Optional<ArtBook>
     */
    Optional<ArtBook> getArtBookById(Long id);

    /**
     * The method allows you to add scientific books.
     * @param scientific scientific book
     * @return Status
     */
    Status addScientificBook(Scientific scientific);

    /**
     * The method allows you to review scientific books.
     * @param id book identifier
     * @return Optional<Scientific>
     */
    Optional<Scientific> getScientificBookById(Long id);

    /**
     * The method allows you to review children's books.
     * @param children children's book
     * @return Status
     */
    Status addChildren(Children children);

    /**
     * The method allows you to review children's books.
     * @param id book identifier
     * @return Optional<Children>
     */
    Optional<Children> getChildrenBookById(Long id);

    /**
     * The method allows you to add books to libraries.
     * @param library library
     * @return Status
     */
    Status addBookToLibrary(Library library);

    /**
     * The method checks if the age constraints are met.
     * @param userAge Library class object
     * @return Library
     */
    Library checkAge(Library userAge);

    /**
     * The method allows you to view reviews and ratings compiled by the user
     * @param method user action
     * @param userId user identifier
     * @return Status
     */
    Status informationReceipt(String method, Long userId);

    /**
     * The method allows you to view reviews compiled by the user
     * @param userId user identifier
     * @return List<String>
     */
    List<String> allUserReviews(Long userId);

    /**
     * The method allows you to view the ratings set by the user
     * @param userId user identifier
     * @return List<Short>
     */
    List<Short> allUserRatings(Long userId);

    /**
     * The method allows you to delete a book by type and id
     * @param typeOfBook enum, type of book
     * @param id book identifier
     * @return Status
     */
    Status delBookByTypeAndId(TypeOfBook typeOfBook, Long id);

    /**
     * The method allows you to view the book by type and id
     * @param typeOfBook enum, type of book
     * @param id book identifier
     * @return Optional<? extends Book>
     */
    Optional<? extends Book> getBookByTypeAndId(TypeOfBook typeOfBook, Long id);

    /**
     * The method allows you to remove a book from the library
     * @param book book
     * @return Status
     */
    Status delBookInLibrary(Book book);

    /**
     * The method allows you to add users.
     * @param user user
     * @return Status
     */
    Status createUser(User user);

    /**
     * The method allows you to view users.
     * @param userId user identifier
     * @return Optional<User>
     */
    Optional<User> getUserById(Long userId);

    /**
     * The method allows you to change the users.
     * @param user user
     * @return Status
     */
    Status updateUser(User user);

    /**
     * The method allows you to remove a user by id.
     * @param userId user identifier
     * @return Status
     */
    Status deleteUserById(Long userId);
}
