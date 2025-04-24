package com.bookstore.exception;

public class AuthorNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public AuthorNotFoundException(String message) {
        super(message);
    }
    
    public AuthorNotFoundException(Long id) {
        super("Author with ID " + id + " does not exist.");
    }
}
