package ru.study.library.model;


import java.util.Objects;


public class Library {

    private Long id;

    private Book book;

    private User user;

    private String review;

    private Short rating;

    public Library(Long id, Book book, User user, String review, Short rating) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.review = review;
        this.rating = rating;
    }

    public Library(Long id, String review, Short rating) {
        this.id = id;
        this.review = review;
        this.rating = rating;
    }

    public Library() {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public Short getRating() { return rating; }
    public void setRating(Short rating) { this.rating = rating; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(id, library.id) &&
                Objects.equals(book, library.book) &&
                Objects.equals(user, library.user) &&
                Objects.equals(review, library.review) &&
                Objects.equals(rating, library.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, review, rating);
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                '}';
    }
}
