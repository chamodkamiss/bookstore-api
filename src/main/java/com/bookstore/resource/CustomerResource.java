package com.bookstore.resource;

import java.net.URI;
import java.util.List;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.Customer;
import com.bookstore.repo.CustomerRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private CustomerRepository customerRepository = new CustomerRepository();

    @POST
    public Response createCustomer(Customer customer) {
        validateCustomer(customer);
        Customer savedCustomer = customerRepository.save(customer);
        return Response.created(URI.create("/customers/" + savedCustomer.getId()))
                .entity(savedCustomer)
                .build();
    }

    @GET
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Customer findById(@PathParam("id") Long id) {
        if (id == null) {
            throw new InvalidInputException("Customer id cannot be null");  
        }
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
        return customer;
    }

    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        if (id == null) {
            throw new InvalidInputException("Customer id cannot be null");  
        }
        if (customerRepository.findById(id) == null) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
        validateCustomer(customer);
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        if (id == null) {
            throw new InvalidInputException("Customer id cannot be null");  
        }
        if (customerRepository.findById(id) == null) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
        customerRepository.deleteById(id);
        return Response.ok()
                .entity("Customer with id " + id + " deleted successfully.")
                .build();
    }

    //Helper method for customer validation
    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be null or empty");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be null or empty");
        }
    }
}
