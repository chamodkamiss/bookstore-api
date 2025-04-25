package com.bookstore.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.Order;

public class OrderRepository {
    private static final Map<Long, Order> orders = new HashMap<>();
    private static Long idCounter = 1L;
    
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idCounter++);
        }
        orders.put(order.getId(), order);
        return order;
    }

    public List<Order> findAll(){
        return new ArrayList<>(orders.values());
    }

    public Order findById(Long id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new InvalidInputException("Order with id " + id + " not found");
        }
        return order;
    }

    public List<Order> findByCustomerId(Long customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }
}
