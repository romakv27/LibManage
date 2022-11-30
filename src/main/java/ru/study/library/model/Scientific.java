package ru.study.library.model;

import org.simpleframework.xml.Attribute;
import ru.study.library.enums.TypeOfBook;

import java.util.Objects;

public class Scientific extends Book {

    @Attribute
    private String direction;

    @Attribute
    private Boolean forStudy;

    public Scientific(Long id, String title, String author, Integer numberOfPages, Integer ageRestriction, String direction, Boolean forStudy) {
        super(id, title, author, numberOfPages, ageRestriction,  TypeOfBook.SCIENTIFIC);
        this.direction = direction;
        this.forStudy = forStudy;
    }

    public Scientific() { };

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public Boolean getForStudy() { return forStudy; }
    public void setForStudy(Boolean forStudy) { this.forStudy = forStudy; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Scientific that = (Scientific) o;
        return Objects.equals(direction, that.direction) &&
                Objects.equals(forStudy, that.forStudy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), direction, forStudy);
    }

    @Override
    public String toString() {
        return "Scientific{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", ageRestriction=" + ageRestriction +
                ", typeOfBook=" + typeOfBook +
                ", direction='" + direction + '\'' +
                ", forStudy=" + forStudy +
                '}';
    }
}
