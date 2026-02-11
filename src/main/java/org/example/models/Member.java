package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Member {
    private String memberId;
    private String name;
    private String email;
    private LocalDate membershipDate;
    private List<Book> borrowedBooks;
    private int maxBooksAllowed;
    private boolean active;

    public static final int DEFAULT_MAX_BOOKS = 5;

    public Member(String memberId, String name, String email) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.membershipDate = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
        this.maxBooksAllowed = DEFAULT_MAX_BOOKS;
        this.active = true;
    }

    public boolean canBorrow() {
        return active && borrowedBooks.size() < maxBooksAllowed;
    }


    public boolean borrowBook(Book book) {
        if (!canBorrow() || book == null || !book.isAvailable()) {
            return false;
        }
        borrowedBooks.add(book);
        book.decreaseQuantity();
        return true;
    }

    public boolean hasBorrowed(Book book) {
        return borrowedBooks.contains(book);
    }
}