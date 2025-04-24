package com.bookstore.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.model.Author;

public class AuthorRepository {
    private static final Map<Long, Author> authors = new HashMap<>();
    private static Long idCounter = 1L;

    public Author save(Author author){
        if (author.getId() == null) {
            author.setId(idCounter++);
        }
        authors.put(author.getId(), author);
        return author;
    }

    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    public Author findById(Long id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with id " + id + " not found");
        }
        return author;
    }

    public void delete(Long id) {
        if (! authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author with id " + id + " not found");
        }
        authors.remove(id);
    }

}
