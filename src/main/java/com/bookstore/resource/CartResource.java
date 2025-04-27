package com.bookstore.resource;

import com.bookstore.exception.InvalidInputException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.repo.BookRepository;
import com.bookstore.repo.CartRepository;
import com.bookstore.repo.CustomerRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private CartRepository cartRepository = new CartRepository();
    private BookRepository bookRepository = new BookRepository();
    private CustomerRepository customerRepository = new CustomerRepository();

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") Long customerId, CartItem item) {
        validateCustomerId(customerId);
        validateCartItem(item);
        // Validate customer exist
        customerRepository.findById(customerId);
        // Validate book exist
        bookRepository.findById(item.getBookId());
        // Validate stock availability
        if (!bookRepository.checkStock(item.getBookId(), item.getQuantity())) {
            throw new OutOfStockException("Not enough stock available for book with ID " + item.getBookId());
        }
        // Add item to cart
        cartRepository.addItemToCart(customerId, item);
        Cart updatedCart = cartRepository.getCartForCustomer(customerId);
        return Response.status(Response.Status.CREATED)
                .entity(updatedCart)
                .build();

    }

    @GET
    public Cart getCart(@PathParam("customerId") Long customerId) {
        validateCustomerId(customerId);
        // Validate customer exist
        customerRepository.findById(customerId);
        return cartRepository.getCartForCustomer(customerId);
    }

    @PUT
    @Path("/items/{bookId}")
    public Cart updateCartItem(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId, @QueryParam("quantity") int quantity) {
        validateCustomerId(customerId);
        validateBookId(bookId);
        validateQuantity(quantity);
        
        customerRepository.findById(customerId);
        // Validate book exist
        bookRepository.findById(bookId);
        // Validate stock availability  
        if (!bookRepository.checkStock(bookId, quantity)) {
            throw new OutOfStockException("Not enough stock available for book with ID " + bookId);
        }
        cartRepository.updateCartItem(customerId, bookId, quantity);
        return cartRepository.getCartForCustomer(customerId);
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItemFromCart(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId) {
        validateCustomerId(customerId);
        validateBookId(bookId);

        customerRepository.findById(customerId);
        cartRepository.removeItemFromCart(customerId, bookId);
        Cart updatedCart = cartRepository.getCartForCustomer(customerId);
        
        return updatedCart.getItems().isEmpty() 
               ? Response.status(Response.Status.NO_CONTENT).build() 
               : Response.ok(updatedCart).build();
    }

    // Helper method for validation
    private void validateCustomerId(Long customerId) {
        if (customerId == null) {
            throw new InvalidInputException("Customer ID cannot be null or empty");
        }
    }
    private void validateBookId(Long bookId) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null or empty");
        }
    }
    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero");
        }
    }
    private void validateCartItem(CartItem item) {
        if (item == null) {
            throw new InvalidInputException("Cart item cannot be null");
        }
        if (item.getBookId() == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }
        if (item.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero");
        }
    }

}
