package com.bookstore.repo;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository {
    private static final Map<Long, Book> books = new HashMap<>();
    private static Long idCounter = 1L;

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter++);
        }
        books.put(book.getId(), book);
        return book;
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Book findById(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        return book;
    }

    public void delete(Long id) {
        books.remove(id);
    }

    public List<Book> findByAuthorId(Long authorId) {
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId().equals(authorId)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    public boolean checkStock(Long bookId, int requestedQuantity) {
        Book book = findById(bookId);
        if (book == null) {
            return false;
        }
        return book.getStock() >= requestedQuantity;
    }

    public boolean decreaseStock(Long id, int quantity){
        Book book = findById(id);
        if(book.getStock() < quantity){
            return false;
        }
        book.setStock(book.getStock() - quantity);
        books.put(id, book);
        return true;
    }


}
