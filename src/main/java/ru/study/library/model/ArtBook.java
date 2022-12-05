package ru.study.library.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import ru.study.library.enums.TypeOfBook;

import java.util.Objects;

public class ArtBook extends Book{

    @Attribute
    @CsvBindByPosition(position = 6)
    protected String genre;

    @Attribute
    @CsvBindByPosition(position = 7)
    protected Boolean comics;

    public ArtBook(Long id, String title, String author, Integer numberOfPages, Integer ageRestriction, TypeOfBook typeOfBook, String genre, Boolean comics) {
        super(id, title, author, numberOfPages, ageRestriction, typeOfBook);
        this.genre = genre;
        this.comics = comics;
    }

    public ArtBook(Long id, String title, String author, Integer numberOfPages, Integer ageRestriction, String genre, Boolean comics) {
        super(id, title, author, numberOfPages, ageRestriction, TypeOfBook.ART);
        this.genre = genre;
        this.comics = comics;
    }

    public ArtBook() {}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    public Boolean getComics() {return comics;}
    public void setComics(Boolean comics) {this.comics = comics;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArtBook artBook = (ArtBook) o;
        return Objects.equals(genre, artBook.genre) &&
                Objects.equals(comics, artBook.comics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genre, comics);
    }

    @Override
    public String toString() {
        return "ArtBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", ageRestriction=" + ageRestriction +
                ", typeOfBook=" + typeOfBook +
                ", genre='" + genre + '\'' +
                ", comics=" + comics +
                '}';
    }
}
