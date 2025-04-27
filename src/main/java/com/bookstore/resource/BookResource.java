package com.bookstore.resource;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.Book;
import com.bookstore.repo.AuthorRepository;
import com.bookstore.repo.BookRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private BookRepository bookRepository = new BookRepository();
    private AuthorRepository authorRepository = new AuthorRepository();

    @POST
    public Response createBook(Book book) {
        // Validate the book object before saving
        validateBook(book);
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
        validateBook(book);
        return bookRepository.save(book);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        if (bookRepository.findById(id) == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.delete(id);
        return Response.noContent().build();
    }

    //Helper method for book validation
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidInputException("Book title cannot be null or empty");
        }
        if (book.getAuthorId() == null || book.getAuthorId() == null) {
            throw new InvalidInputException("Book author cannot be null or empty");
        }
        if (authorRepository.findById(book.getAuthorId()) == null) {
            throw new InvalidInputException("Author with id " + book.getAuthorId() + " does not exist");
        }
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Book price must be greater than zero");
        }
        if (book.getStock() < 0) {
            throw new InvalidInputException("Book stock cannot be negative");
        }
        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            throw new InvalidInputException("Book publication year cannot be in the future");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new InvalidInputException("Book ISBN cannot be null or empty");
        }
    }

}
