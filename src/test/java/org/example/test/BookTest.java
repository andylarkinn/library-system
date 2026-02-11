package org.example.test;

import org.example.models.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Test Class")
public class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("cleanCodeIsbn", "Clean Code", "Robert Martin", 45.99);
    }

    @AfterEach
    void tearDown() {
        book = null;
    }


    @Test
    void testBookIsbn() {
        assertEquals("cleanCodeIsbn", book.getIsbn(), "ISBN should match");
    }

    @Test
    void testBookTitle() {
        assertEquals("Clean Code", book.getTitle(), "Title should match");
    }

    @Test
    void testBookAuthor() {
        assertEquals("Robert Martin", book.getAuthor(), "Author should match");
    }

    @Test
    void testBookPrice() {
        assertEquals(45.99, book.getPrice(), 0.01, "Price should match");
    }

    @Test
    void testInitialQuantity() {
        assertEquals(1, book.getQuantity(), "Initial quantity should be 1");
    }

    @Test
    void testNewBookIsAvailable() {
        assertTrue(book.isAvailable(), "New book should be available");
    }

    @Test
    void testBookUnavailableWhenQuantityZero() {
        book.decreaseQuantity();
        assertFalse(book.isAvailable(), "Book should be unavailable when quantity is 0");
    }

    @Test
    void testBookAvailableAfterIncreasingQuantity() {
        book.decreaseQuantity();
        book.increaseQuantity();
        assertTrue(book.isAvailable(), "Book should be available after increasing quantity");
    }

    @Test
    void testBookNotNull() {
        assertNotNull(book, "Book object should not be null");
    }

    @Test
    void testBookCategoryInitiallyNull() {
        assertNull(book.getCategory(), "Category should initially be null");
    }

    @Test
    void testBookCategoryNotNullAfterSetting() {
        book.setCategory("Programming");
        assertNotNull(book.getCategory(), "Category should not be null after setting");
    }

    @Test
    void testNullIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Book(null, "Title", "Author", 10.0),
            "Should throw exception for null ISBN");
    }

    @Test
    void testEmptyIsbnThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Book("", "Title", "Author", 10.0),
            "Should throw exception for empty ISBN");
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Book("123", "Title", "Author", -10.0),
            "Should throw exception for negative price");
    }

    @Test
    void testInvalidDiscountThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> book.applyDiscount(150),
            "Should throw exception for discount > 100");
    }

    @Test
    void testSameBookReference() {
        Book sameBook = book;
        assertSame(book, sameBook, "References should point to same object");
    }

    @Test
    void testDifferentBookObjects() {
        Book differentBook = new Book("randomIsbn", "Clean Code", "Robert Martin", 45.99);
        assertNotSame(book, differentBook, "Different objects should not be same");
    }

    @Test
    void testAllBookProperties() {
        assertAll("Book properties",
            () -> assertEquals("cleanCodeIsbn", book.getIsbn()),
            () -> assertEquals("Clean Code", book.getTitle()),
            () -> assertEquals("Robert Martin", book.getAuthor()),
            () -> assertEquals(45.99, book.getPrice(), 0.01),
            () -> assertTrue(book.isAvailable())
        );
    }

    @Test
    void testBookCreationTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            new Book("ISBN-TEST", "Test Book", "Test Author", 25.00);
        }, "Book creation should be fast");
    }

    @ParameterizedTest
    @CsvSource({
        "10, 41.39",
        "20, 36.79",
        "25, 34.49",
        "50, 23.00",
        "0, 45.99"
    })
    void testDiscountCalculations(double discount, double expectedPrice) {
        assertEquals(expectedPrice, book.applyDiscount(discount), 0.01,
            "Discount calculation should be correct");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123-456", "ISBN-001", "978-0-13-468599-1"}) // won't pass with null or empty string
    void testValidIsbnFormats(String isbn) {
        Book testBook = new Book(isbn, "Test", "Author", 10.0);
        assertNotNull(testBook, "Book should be created with valid ISBN");
    }
}