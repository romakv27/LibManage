package ru.study.library.model;

import org.simpleframework.xml.Attribute;
import ru.study.library.enums.TypeOfBook;

import java.util.Objects;

public class Children extends ArtBook{

    @Attribute
    private Boolean educational;

    @Attribute
    private Boolean interactive;

    public Children(Long id, String title, String author, Integer numberOfPages, Integer ageRestriction, String genre, Boolean comics, Boolean educational, Boolean interactive) {
        super(id, title, author, numberOfPages, ageRestriction,  TypeOfBook.CHILDREN, genre, comics);
        this.educational = educational;
        this.interactive = interactive;
    }

    public Children() { };

    public Boolean getEducational() { return educational; }
    public void setEducational(Boolean educational) { this.educational = educational; }

    public Boolean getInteractive() { return interactive; }
    public void setInteractive(Boolean interactive) { this.interactive = interactive; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Children children = (Children) o;
        return Objects.equals(educational, children.educational) &&
                Objects.equals(interactive, children.interactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), educational, interactive);
    }

    @Override
    public String toString() {
        return "Children{" +
                " id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", ageRestriction=" + ageRestriction +
                ", typeOfBook=" + typeOfBook +
                ", educational=" + educational +
                ", interactive=" + interactive +
                ", genre='" + genre + '\'' +
                ", comics=" + comics +
                '}';
    }
}
