package org.example.test;

import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Loan;
import org.example.models.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Library Test Class")
public class LibraryTest {

    private Library library;
    private Book book1, book2, book3;
    private Member member1;

    @BeforeEach
    void setUp() {
        library = new Library("City Library");
        book1 = new Book("isbn1", "Java Programming", "James Gosling", 50.00);
        book2 = new Book("isbn2", "Python Basics", "Guido van Rossum", 35.00);
        book3 = new Book("isbn3", "Java Collections", "Joshua Bloch", 45.00);
        member1 = new Member("mem1", "Alice Smith", "alice@email.com");
    }


    @Test
    void testBookPricesArray() {
        library.addBook(book1);
        library.addBook(book2);
        double[] expected = {50.00, 35.00};
        assertArrayEquals(expected, library.getBookPrices(), 0.01,
            "Book prices array should match");
    }

    @Test
    void testBookTitlesArray() {
        library.addBook(book1);
        library.addBook(book2);
        String[] expected = {"Java Programming", "Python Basics"};
        assertArrayEquals(expected, library.getBookTitles(),
            "Book titles array should match");
    }

    @Test
    void testFindBooksByAuthor() {
        library.addBook(book1);
        library.addBook(book2);
        List<Book> javaBooks = library.findBooksByAuthor("James Gosling");
        assertEquals(1, javaBooks.size(), "Should find 1 book by James Gosling");
    }

    @Test
    void testFindBooksByTitleKeyword() {
        library.addBook(book1);
        library.addBook(book3);
        List<Book> javaBooks = library.findBooksByTitle("Java");
        assertEquals(2, javaBooks.size(), "Should find 2 books with 'Java' in title");
    }

    @Test
    void testLibraryName() {
        assertEquals("City Library", library.getName(), "Library name should match");
    }

    @Test
    void testAddBookIncreasesCount() {
        library.addBook(book1);
        assertEquals(1, library.getTotalBooks(), "Total books should be 1");
    }

    @Test
    void testRemoveBookDecreasesCount() {
        library.addBook(book1);
        library.removeBook("isbn1");
        assertEquals(0, library.getTotalBooks(), "Total books should be 0");
    }

    @Test
    void testLendBookReturnsLoan() {
        library.addBook(book1);
        library.addMember(member1);
        Loan loan = library.lendBook("mem1", "isbn1");
        assertNotNull(loan, "Loan should not be null");
    }

    @ParameterizedTest
    @MethodSource("provideBooks")
    void testAddMultipleBooks(Book book) {
        assertTrue(library.addBook(book), "Adding book should return true");
        assertNotNull(library.findBookByIsbn(book.getIsbn()), 
            "Book should be findable after adding");
    }

    static Stream<Book> provideBooks() {
        return Stream.of(
            new Book("isbn1", "Book One", "Author One", 20.00),
            new Book("isbn2", "Book Two", "Author Two", 25.00),
            new Book("isbn3", "Book Three", "Author Three", 30.00)
        );
    }
}