package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.BookNotFoundExceptionMapper;
import com.bookstore.exception.CustomerNotFoundExceptionMapper;
import com.bookstore.resource.AuthorResource;
import com.bookstore.resource.BookResource;
import com.bookstore.resource.CustomerResource;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
public class BookstoreApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Register resources
        classes.add(BookResource.class);
        classes.add(AuthorResource.class);
        classes.add(CustomerResource.class);

        // Register exception mappers
        classes.add(BookNotFoundExceptionMapper.class);
        classes.add(AuthorNotFoundException.class);
        classes.add(CustomerNotFoundExceptionMapper.class);
        return classes;
    }
}
