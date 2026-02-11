package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
public class Library {
    private String name;
    private List<Book> books;
    private List<Member> members;
    private Map<String, Loan> activeLoans;


    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.activeLoans = new HashMap<>();
    }

    public int getTotalBooks() { return books.size(); }

    public boolean addBook(Book book) {
        if (book == null) return false;
        if (findBookByIsbn(book.getIsbn()) != null) {
            findBookByIsbn(book.getIsbn()).increaseQuantity();
            return true;
        }
        return books.add(book);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByTitle(String titleKeyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .contains(titleKeyword.toLowerCase()))
                .collect(Collectors.toList());
    }


    public void addMember(Member member) {
        if (member == null) return;
        if (findMemberById(member.getMemberId()) != null) return;
        members.add(member);
    }


    public Member findMemberById(String memberId) {
        return members.stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElse(null);
    }

    public Loan lendBook(String memberId, String isbn) {
        var member = findMemberById(memberId);
        var book = findBookByIsbn(isbn);
        
        if (member == null || book == null || !member.canBorrow() || !book.isAvailable()) {
            return null;
        }
        
        member.borrowBook(book);
        var loan = new Loan.LoanBuilder()
                .loanId(UUID.randomUUID().toString())
                .book(book)
                .member(member)
                .build();
        activeLoans.put(loan.getLoanId(), loan);
        return loan;
    }

    public double[] getBookPrices() {
        return books.stream().mapToDouble(Book::getPrice).toArray();
    }

    public String[] getBookTitles() {
        return books.stream().map(Book::getTitle).toArray(String[]::new);
    }
}