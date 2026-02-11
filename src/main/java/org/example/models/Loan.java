package org.example.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Loan {
    private String loanId;
    private Book book;
    private Member member;
}