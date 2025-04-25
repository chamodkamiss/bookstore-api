package com.bookstore.resource;

import java.util.Date;
import java.util.List;

import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.repo.BookRepository;
import com.bookstore.repo.CartRepository;
import com.bookstore.repo.CustomerRepository;
import com.bookstore.repo.OrderRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private OrderRepository orderRepository = new OrderRepository();
    private CustomerRepository customerRepository = new CustomerRepository();
    private CartRepository cartRepository = new CartRepository();
    private BookRepository bookRepository = new BookRepository();

    @POST
    public Response createOrder(@PathParam("customerId") Long customerId) {
        // Validate customer exist
        customerRepository.findById(customerId);
        // Get customer's cart
        Cart cart = cartRepository.getCartForCustomer(customerId);

        // Check if cart is empty
        if (cart.getItems().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cart is empty. Cannot create order.")
                    .build();    
        }

        // Check stock availability and calculate total amount
        double totalAmount = 0;
        for (CartItem item : cart.getItems()){
            // Check stock
            if (!bookRepository.decreaseStock(item.getBookId(), item.getQuantity())) {
                throw new OutOfStockException("Not enough stock for book with id " + item.getBookId());
            }

            // Calculate item price
            double itemPrice = bookRepository.findById(item.getBookId()).getPrice()*item.getQuantity();
            totalAmount += itemPrice;
        }

        // Create new order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(cart.getItems());
        order.setTotalAmount(totalAmount);
        order.setOrderDate(new Date());

        //save order
        Order savedOrder = orderRepository.save(order);
        // Clear cart after order creation
        cartRepository.clearCart(customerId);

        return Response.status(Response.Status.CREATED)
                .entity(savedOrder)
                .build();
    }

    @GET
    public List<Order> getCustomOrders(@PathParam("customerId") Long customerId) {
        // Validate customer exist
        customerRepository.findById(customerId);
        // Get orders for the customer
        return orderRepository.findByCustomerId(customerId);   
    }

    @GET
    @Path("/{orderId}")
    public Order getOrderById(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        // Validate customer exist
        customerRepository.findById(customerId);
        // Get order by id
        Order order = orderRepository.findById(orderId);
        // Check if order belongs to the customer
        if (!order.getCustomerId().equals(customerId)) {
            throw new WebApplicationException("Order with id " + orderId + " does not belong to customer with id " + customerId, Response.Status.FORBIDDEN);
        }
        return order;
    }
}
