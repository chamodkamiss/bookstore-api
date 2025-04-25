package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import com.bookstore.exception.AuthorNotFoundExceptionMapper;
import com.bookstore.exception.BookNotFoundExceptionMapper;
import com.bookstore.exception.CartNotFoundExceptionMapper;
import com.bookstore.exception.CustomerNotFoundExceptionMapper;
import com.bookstore.exception.InvalidInputExceptionMapper;
import com.bookstore.exception.OutOfStockExceptionMapper;
import com.bookstore.resource.AuthorResource;
import com.bookstore.resource.BookResource;
import com.bookstore.resource.CartResource;
import com.bookstore.resource.CustomerResource;
import com.bookstore.resource.OrderResource;

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
        classes.add(CartResource.class);
        classes.add(OrderResource.class);

        // Register exception mappers
        classes.add(BookNotFoundExceptionMapper.class);
        classes.add(AuthorNotFoundExceptionMapper.class);
        classes.add(CustomerNotFoundExceptionMapper.class);
        classes.add(CartNotFoundExceptionMapper.class);
        classes.add(OutOfStockExceptionMapper.class);
        classes.add(InvalidInputExceptionMapper.class);
        
        return classes;
    }
}
