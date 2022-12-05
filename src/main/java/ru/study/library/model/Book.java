package ru.study.library.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import ru.study.library.enums.TypeOfBook;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {

    @Element
    @CsvBindByPosition(position = 0)
    protected Long id;

    @Attribute
    @CsvBindByPosition(position = 1)
    protected String title;

    @Attribute
    @CsvBindByPosition(position = 2)
    protected String author;

    @Attribute
    @CsvBindByPosition(position = 3)
    protected Integer numberOfPages;

    @Attribute
    @CsvBindByPosition(position = 4)
    protected Integer ageRestriction;

    @Attribute
    @CsvBindByPosition(position = 5)
    protected TypeOfBook typeOfBook;

    public Book(Long id, String title, String author, Integer numberOfPages, Integer ageRestriction, TypeOfBook typeOfBook) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.ageRestriction = ageRestriction;
        this.typeOfBook = typeOfBook;
    }

    public Book() { };

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; }

    public Integer getAgeRestriction() { return ageRestriction; }
    public void setAgeRestriction(Integer ageRestriction) { this.ageRestriction = ageRestriction; }

    public TypeOfBook getTypeOfBook() { return typeOfBook; }
    public void setTypeOfBook(TypeOfBook typeOfBook) { this.typeOfBook = typeOfBook; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(numberOfPages, book.numberOfPages) &&
                Objects.equals(ageRestriction, book.ageRestriction) &&
                typeOfBook == book.typeOfBook;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, numberOfPages, ageRestriction, typeOfBook);
    }


}
