package ru.study.library.api;

import ru.study.library.enums.Status;
import ru.study.library.enums.TypeOfBook;
import ru.study.library.model.*;

import java.util.List;
import java.util.Optional;

public interface IDataProvider {

    Status addBook(String method, ArtBook artBook, Scientific scientific, Children children);

    Status addArtBook(ArtBook artBook);

    Optional<ArtBook> getArtBookById(Long id);

    Status addScientificBook(Scientific scientific);

    Optional<Scientific> getScientificBookById(Long id);

    Status addChildren(Children children);

    Optional<Children> getChildrenBookById(Long id);

    Status addBookToLibrary(Library library);

    Library checkAge(Library userAge);

    Status informationReceipt(String method, Long userId);

    List<String> allUserReviews(Long userId);

    List<Short> allUserRatings(Long userId);

    Status delBookByTypeAndId(TypeOfBook typeOfBook, Long id);

    Optional<? extends Book> getBookByTypeAndId(TypeOfBook typeOfBook, Long id);

    Status delBookInLibrary(Book book);

    Status createUser(User user);
    Optional<User> getUserById(Long userId);
    Status updateUser(User user);
    Status deleteUserById(Long userId);
}
