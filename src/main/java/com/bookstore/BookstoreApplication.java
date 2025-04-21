package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import com.bookstore.exception.BookNotFoundExceptionMapper;
import com.bookstore.resource.BookResource;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
public class BookstoreApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(BookResource.class);
        classes.add(BookNotFoundExceptionMapper.class);
        return classes;
    }
}
