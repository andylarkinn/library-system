package org.example.test;

import org.example.models.Book;
import org.example.models.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Member Test Class")
public class MemberTest {

    private Member member;
    private Book book;

    @BeforeEach
    void setUp() {
        member = new Member("mem1", "John Doe", "john@email.com");
        book = new Book("isbn1", "Test Book", "Test Author", 25.00);
    }

    @Test
    void testMemberId() {
        assertEquals("mem1", member.getMemberId(), "Member ID should match");
    }

    @Test
    void testDefaultMaxBooks() {
        assertEquals(Member.DEFAULT_MAX_BOOKS, member.getMaxBooksAllowed(),
            "Default max books should be 5");
    }

    @Test
    void testNewMemberCanBorrow() {
        assertTrue(member.canBorrow(), "New member should be able to borrow");
    }

    @Test
    void testInactiveMemberCannotBorrow() {
        member.setActive(false);
        assertFalse(member.canBorrow(), "Inactive member cannot borrow");
    }

    @Test
    void testMemberAtLimitCannotBorrow() {
        member.setMaxBooksAllowed(1);
        member.borrowBook(book);
        assertFalse(member.canBorrow(), "Member at limit cannot borrow");
    }

    @Test
    void testBorrowBookSuccess() {
        assertTrue(member.borrowBook(book), "Borrowing should succeed");
    }

    @Test
    void testHasBorrowed() {
        member.borrowBook(book);
        assertTrue(member.hasBorrowed(book), "Member should have borrowed the book");
    }

    @Test
    void testNullMemberIdThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Member(null, "Name", "email@test.com"),
            "Should throw exception for null member ID");
    }

    @Test
    void testInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Member("mem2", "Name", "invalidemail"),
            "Should throw exception for invalid email");
    }

    @Test
    void testMembershipDateNotNull() {
        assertNotNull(member.getMembershipDate(), "Membership date should not be null");
    }

    @Test
    void testBorrowedBooksListNotNull() {
        assertNotNull(member.getBorrowedBooks(), "Borrowed books list should not be null");
    }

    @Test
    void testBorrowedBooksList() {
        member.borrowBook(book);
        assertEquals(1, member.getBorrowedBooks().size(), "Should have 1 borrowed book");
        assertTrue(member.getBorrowedBooks().contains(book), "List should contain the borrowed book");
    }
}