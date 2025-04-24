package com.bookstore.resource;

import java.net.URI;
import java.util.List;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.repo.AuthorRepository;
import com.bookstore.repo.BookRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private AuthorRepository authorRepository = new AuthorRepository();
    private BookRepository bookRepository = new BookRepository();

    @POST
    public Response createAuthor(Author author){
        Author createdAuthor = authorRepository.save(author);
        return Response.created(URI.create("/authors/"+ createdAuthor.getId()))
        .entity(createdAuthor)
        .build();
    }

    @GET
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Author findById(@PathParam("id") Long id){
        Author author = authorRepository.findById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with id "+ id + " not found.");
        }
        return author;
    }

    @PUT
    @Path("/{id}")
    public Author updateAuthor (@PathParam("id") Long id,Author author){
        if (authorRepository.findById(id) == null) {
            throw new AuthorNotFoundException("Author with id "+ id + " not found.");
        }
        author.setId(id);
        return authorRepository.save(author);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id){
        if (authorRepository.findById(id) == null) {
            throw new AuthorNotFoundException("Author with id "+ id + " not found.");
        }
        authorRepository.delete(id);
        return Response.ok()
        .entity("Author with id " + id + " deleted successfully.")
        .build();
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthorId(@PathParam("id") Long id){
        if (authorRepository.findById(id) == null) {
            throw new AuthorNotFoundException("Author with id "+ id + " not found.");
        }
        return bookRepository.findByAuthorId(id);
    }
    
}
