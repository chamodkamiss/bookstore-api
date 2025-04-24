package com.bookstore.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Customer;

public class CustomerRepository {
    private static final Map<Long, Customer> customers = new HashMap<>();
    private static long idCounter = 1L;

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(idCounter++);
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    public List <Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    public Customer findById(Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        return customer;
    }

    public void deleteById(Long id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customers.remove(id);
    }
}
