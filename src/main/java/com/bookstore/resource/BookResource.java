package com.bookstore.resource;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repo.BookRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private BookRepository bookRepository = new BookRepository();

    @POST
    public Response createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return Response.created(URI.create("/books/" + savedBook.getId()))
                .entity(savedBook)
                .build();
    }

    @GET
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Book findById(@PathParam("id") Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        return book;
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        if (bookRepository.findById(id) == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        if (bookRepository.findById(id) == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.delete(id);
        return Response.ok()
        .entity("Book with id " + id + " deleted successfully.")
        .build();
    }

}
