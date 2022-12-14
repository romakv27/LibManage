package ru.study.library.api;

import org.junit.Test;
import ru.study.library.enums.Status;
import ru.study.library.model.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;

public class DataProviderXMLTest extends BaseTest {
    final IDataProvider provider = new DataProviderXML();
    Status result;

    @Test
    public  void testAddBook(){
        ArtBook artBook = getArtBook();
        Scientific scientific = getScientific();
        Children children = getChildren();

        result = provider.addBook("ART",artBook,scientific,children);
        System.out.printf("POSITIVE — add book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addBook("ARTT",artBook,scientific,children);
        System.out.printf("NEGATIVE — add book: %s\n", result.name());
        assertEquals(result, FAIL);


        result = provider.addBook("SCIENTIFIC",artBook,scientific,children);
        System.out.printf("POSITIVE — add book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addBook("SAIENTIFIC",artBook,scientific,children);
        System.out.printf("NEGATIVE — add book: %s\n", result.name());
        assertEquals(result, FAIL);


        result = provider.addBook("CHILDREN",artBook,scientific,children);
        System.out.printf("POSITIVE — add book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addBook("XHILDREN",artBook,scientific,children);
        System.out.printf("NEGATIVE — add book: %s\n", result.name());
        assertEquals(result, FAIL);

    }

    @Test
    public void testAddArtBook(){
        ArtBook artBook = getArtBook();

        result = provider.addArtBook(artBook);
        System.out.printf("POSITIVE — Add Art book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addArtBook(artBook);
        System.out.printf("NEGATIVE — Add Art book: %s\n", result.name());
        assertEquals(result, FAIL);


        assertEquals(provider.delBookByTypeAndId(artBook.getTypeOfBook(), artBook.getId()), SUCCESS);
    }

    @Test
    public void testAddScientificBook(){
        Scientific scientific = getScientific();

        result = provider.addScientificBook(scientific);
        System.out.printf("POSITIVE — Add scientific book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addScientificBook(scientific);
        System.out.printf("NEGATIVE — Add scientific book: %s\n", result.name());
        assertEquals(result, FAIL);

        assertEquals(provider.delBookByTypeAndId(scientific.getTypeOfBook(), scientific.getId()), SUCCESS);
    }

    @Test
    public void testAddChildrenBook(){
        Children children = getChildren();

        result = provider.addChildren(children);
        System.out.printf("POSITIVE — Add children book: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addChildren(children);
        System.out.printf("NEGATIVE — Add children book: %s\n", result.name());
        assertEquals(result, FAIL);

        assertEquals(provider.delBookByTypeAndId(children.getTypeOfBook(), children.getId()), SUCCESS);
    }


    @Test
    public void testAddBookToLibrary(){
        Library library = getLibrary();
        ArtBook artBook = getArtBook();

        result = provider.addBookToLibrary(library);
        System.out.printf("POSITIVE — Add book to Library: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addBookToLibrary(library);
        System.out.printf("NEGATIVE — Add book to Library: %s\n", result.name());
        assertEquals(result, FAIL);


        assertEquals(provider.addArtBook(artBook), SUCCESS);
        Optional<? extends Book> optionalBook = provider.getArtBookById(artBook.getId());
        assertEquals(provider.delBookInLibrary(optionalBook.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(artBook.getTypeOfBook(), artBook.getId()), SUCCESS);
    }

    @Test
    public void testCheckAge(){
        Library library = getLibraryCheck();
        Library library2 = getLibraryCheckPos();

        Children children = getChildren();
        Scientific scientific = getScientific();


        result = provider.addBookToLibrary(library2);
        System.out.printf("POSITIVE — Check age: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.addBookToLibrary(library);
        System.out.printf("NEGATIVE — Check age: %s\n", result.name());
        assertEquals(result, SUCCESS);


        assertEquals(provider.addScientificBook(scientific), SUCCESS);
        Optional<? extends Book> optionalBook = provider.getScientificBookById(scientific.getId());
        assertEquals(provider.delBookInLibrary(optionalBook.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(scientific.getTypeOfBook(), scientific.getId()), SUCCESS);
        assertEquals(provider.addChildren(children), SUCCESS);
        Optional<? extends Book> optionalBook2 = provider.getChildrenBookById(children.getId());
        assertEquals(provider.delBookInLibrary(optionalBook2.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(children.getTypeOfBook(), children.getId()), SUCCESS);
    }

    @Test
    public void testInformationReceipt(){
        Library library = getLibrary();
        assertEquals(provider.addBookToLibrary(library), SUCCESS);

        result = provider.informationReceipt("all_user_reviews", 1L);
        System.out.printf("POSITIVE — info: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.informationReceipt("all_usser_reviews", 1L);
        System.out.printf("NEGATIVE — info: %s\n", result.name());
        assertEquals(result, FAIL);

        result = provider.informationReceipt("all_user_ratings", 1L);
        System.out.printf("POSITIVE — info: %s\n", result.name());
        assertEquals(result, SUCCESS);

        result = provider.informationReceipt("all_user_raitings", 1L);
        System.out.printf("NEGATIVE — info: %s\n", result.name());
        assertEquals(result, FAIL);

        ArtBook artBook = getArtBook();
        assertEquals(provider.addArtBook(artBook), SUCCESS);
        Optional<? extends Book> optionalBook = provider.getArtBookById(artBook.getId());
        assertEquals(provider.delBookInLibrary(optionalBook.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(artBook.getTypeOfBook(), artBook.getId()), SUCCESS);
    }

    @Test
    public void testAllUserReviews(){
        List<String> stringList;

        Library library = getLibrary();
        assertEquals(provider.addBookToLibrary(library), SUCCESS);


        stringList = provider.allUserReviews(1L);
        System.out.printf("POSITIVE — Get reviews by user: %s\n", stringList.toString());
        assertFalse(stringList.isEmpty());

        stringList = provider.allUserReviews(7L);
        System.out.printf("NEGATIVE — Get reviews by user: %s\n", stringList.toString());
        assertTrue(stringList.isEmpty());


        ArtBook artBook = getArtBook();
        assertEquals(provider.addArtBook(artBook), SUCCESS);
        Optional<? extends Book> optionalBook = provider.getArtBookById(artBook.getId());
        assertEquals(provider.delBookInLibrary(optionalBook.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(artBook.getTypeOfBook(), artBook.getId()), SUCCESS);
    }

    @Test
    public void testAllUserRatings(){
        List<Short> shortList;

        Library library = getLibrary();
        assertEquals(provider.addBookToLibrary(library), SUCCESS);

        shortList = provider.allUserRatings(1L);
        System.out.printf("POSITIVE — Get rating by user: %s\n", shortList.toString());
        assertFalse(shortList.isEmpty());

        shortList = provider.allUserRatings(7L);
        System.out.printf("NEGATIVE — Get rating by user: %s\n", shortList.toString());
        assertTrue(shortList.isEmpty());


        ArtBook artBook = getArtBook();
        assertEquals(provider.addArtBook(artBook), SUCCESS);
        Optional<? extends Book> optionalBook = provider.getArtBookById(artBook.getId());
        assertEquals(provider.delBookInLibrary(optionalBook.get()), SUCCESS);
        assertEquals(provider.delBookByTypeAndId(artBook.getTypeOfBook(), artBook.getId()), SUCCESS);
    }
}
