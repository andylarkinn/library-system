package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private boolean available;
    private int quantity;
    private String category;

    public Book(String isbn, String title, String author, double price) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.available = true;
        this.quantity = 1;
    }

    public boolean isAvailable() { return available && quantity > 0; }

    public void decreaseQuantity() {
        if (quantity > 0) quantity--;
        if (quantity == 0) available = false;
    }

    public void increaseQuantity() {
        quantity++;
        available = true;
    }

    public double applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        return price * (1 - percentage / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', title='" + title + "', author='" + author + "'}";
    }
}